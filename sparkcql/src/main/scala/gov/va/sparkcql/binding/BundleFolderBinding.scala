package gov.va.sparkcql.binding

import java.nio.file.{FileSystems, Files}
import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, DataFrame, Row}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.Encoders
import gov.va.sparkcql.model.fhir.r4._

class BundleFolderBinding(spark: SparkSession, path: String) extends TableBinding(spark, TableBindingConfig("http://hl7.org/fhir/fhir-types", List[TableBindingConfigTable]())) {

  import spark.implicits._
  val fabricatedResourceColumnName = "resource"

  lazy val allResourceText = {
    // Load all files for given path into a Seq of strings.
    val files = new java.io.File(path).listFiles()
    val bundleText = files.map(f => {
      try {
        scala.io.Source.fromFile(f)("UTF-8").mkString
      } catch {
        case t: Throwable => throw new Exception(f.getAbsolutePath(), t)
      }
    }).toSeq.toDS
    
    val bundleParsed = spark.read.json(bundleText)
    bundleParsed
      .select(explode(col("entry")).as("entry"))
      .select(
        expr("TO_JSON(STRUCT(entry.resource.*))").as("resourceText"),
        col("entry.resource.resourceType").as("resourceType"))
  }

  def ensureFetched[T <: Product : TypeTag](resourceType: Coding): Unit = {
    val t = tableConfig(resourceType.code)
    if (t.isEmpty) {
      val resourceText = allResourceText
        .where(col("resourceType").equalTo(resourceType.code))
        .select(col("resourceText"))
        .toDF()
            
      val schema = Encoders.product[T].schema
      
      val ds = spark.read.schema(schema).json(resourceText.as[String]).select(struct("*").alias(fabricatedResourceColumnName))
            
      val newTableConfig = TableBindingConfigTable(schema=None, table=resourceType.code, code=resourceType.code, resourceColumn=fabricatedResourceColumnName)
      configuration = configuration.copy(tables=newTableConfig :: configuration.tables)
      ds.createTempView(tableNomenclature(newTableConfig))
    }
  }

  override def retrieve[T <: Product: TypeTag](resourceType: Coding, filter: Option[List[PredicateLike]]): Option[Dataset[T]] = {
    ensureFetched[T](resourceType)
    super.retrieve(resourceType, filter)
  }
}

object BundleFolderBinding {
  def bind[T <: Product: TypeTag](resourceType: Coding, filter: Option[List[PredicateLike]])(implicit tag: TypeTag[T]): Unit = {
    println(s"type arguments ${tag.toString()}")
  }
}