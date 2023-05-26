package gov.va.sparkcql.binding

import java.nio.file.{FileSystems, Files}
import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, DataFrame, Row}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.Encoders
import gov.va.sparkcql.model.elm.{Code, ExpressionLike}
import gov.va.sparkcql.session.CqlSession

class MockDataBinder(bundleFolderPath: String) extends TableBinder {

  def allResourceText(session: CqlSession) = {
    import session.spark.implicits._
    // Load all files for given path into a Seq of strings.
    val files = new java.io.File(bundleFolderPath).listFiles()
    val bundleText = files.map(f => {
      try {
        scala.io.Source.fromFile(f)("UTF-8").mkString
      } catch {
        case t: Throwable => throw new Exception(f.getAbsolutePath(), t)
      }
    }).toSeq.toDS
    
    val bundleParsed = session.spark.read.json(bundleText)
    bundleParsed
      .select(explode(col("entry")).as("entry"))
      .select(
        to_json(struct(col("entry.resource.*"))).as("resourceText"),
        col("entry.resource.resourceType").as("resourceType"))
  }

  def tableNomenclature[T <: Bindable : TypeTag]: String = {
    typeOf[T].typeSymbol.name.toString()
  }

  def ensureFetched(session: CqlSession, bindableTypeCode: Code): Unit = {
    import session.spark.implicits._
    val info = tableInfo(bindableTypeCode)
    val table = tableRef(bindableTypeCode)
    if (!session.spark.catalog.tableExists(table)) {
      val resourceText = allResourceText(session)
        .where(col("resourceType").equalTo(bindableTypeCode.code))    // TODO: ADD FILTER ON MODEL AND VERSION
        .select(col("resourceText"))
        .toDF()
            
      val ds = session.spark.read.json(resourceText.as[String])
        .select(struct("*").alias(DefaultResourceColumnName))

      ds.createTempView(table)
    }
  }

  override def retrieve(session: CqlSession, bindableTypeCode: Code, filter: Option[List[ExpressionLike]]): Option[DataFrame] = {
    import session.spark.implicits._
    ensureFetched(session, bindableTypeCode)
    super.retrieve(session, bindableTypeCode, filter)
  }
}