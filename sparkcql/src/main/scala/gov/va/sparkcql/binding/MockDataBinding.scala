package gov.va.sparkcql.binding

import java.nio.file.{FileSystems, Files}
import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, DataFrame, Row}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.Encoders
import gov.va.sparkcql.model.fhir.r4._
import gov.va.sparkcql.model._
import gov.va.sparkcql.session.Session

final case class MockDataBindingSetting(bundleFolderPath: String)

class MockDataBinding(session: Session, settings: MockDataBindingSetting) extends Binding {

  import session.spark.implicits._

  val FabricatedResourceColumnName = "resource"
  var tableBinding = new TableBinding(session, TableBindingSetting(None, None, None, Some("Index"), None, FabricatedResourceColumnName))
  
  lazy val allResourceText = {
    // Load all files for given path into a Seq of strings.
    val files = new java.io.File(this.settings.bundleFolderPath).listFiles()
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
        expr("TO_JSON(STRUCT(entry.resource.*))").as("resourceText"),
        col("entry.resource.resourceType").as("resourceType"))
  }

  def tableNomenclature[T <: BoundType : TypeTag]: String = {
    typeOf[T].typeSymbol.name.toString()
  }

  def ensureFetched[T <: BoundType : TypeTag](): Unit = {
    val typeName, tableName = tableNomenclature[T]
    if (!session.spark.catalog.tableExists(tableName)) {
      val resourceText = allResourceText
        .where(col("resourceType").equalTo(typeName))
        .select(col("resourceText"))
        .toDF()
            
      val schema = Encoders.product[T].schema
      
      val ds = session.spark.read.schema(schema).json(resourceText.as[String])
        .select(struct("*").alias(FabricatedResourceColumnName))

      ds.createTempView(tableName)
    }
  }

  override def retrieve[T <: BoundType : TypeTag](filter: Option[List[PredicateLike]]): Option[Dataset[T]] = {
    ensureFetched[T]()
    tableBinding.retrieve[T](filter)
  }
}

object MockDataBinding extends BindingFactory {
  override def create(session: Session, settings: Option[Map[String, Any]]): Binding = {
    new MockDataBinding(session, convertSettings[MockDataBindingSetting](settings))
  }
}