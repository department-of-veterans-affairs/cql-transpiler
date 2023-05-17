package gov.va.sparkcql.binding

import java.nio.file.{FileSystems, Files}
import scala.collection.mutable.HashMap
import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, DataFrame}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import org.apache.spark.sql.Encoders
import gov.va.sparkcql.model.fhir._
import gov.va.sparkcql.model.fhir.Primitive._

class BundleFolderBinding(spark: SparkSession, path: String) extends Binding(spark) {

  import spark.implicits._
  var cache = HashMap[Coding, Object]()

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

  def resolve[T <: Product : TypeTag](resourceType: Coding, code: Option[Coding] = None, startDate: Option[DateTime] = None, endDate: Option[DateTime] = None): Dataset[T] = {
    if (!cache.contains(resourceType)) {
      
      val resourceText = allResourceText
        .where(col("resourceType").equalTo(resourceType.code))
        .select(col("resourceText"))
        .toDF()

      val schema = Encoders.product[T].schema
      val ds = spark.read.schema(schema).json(resourceText.as[String]).as[T]
      
      cache.put(resourceType, ds)
    }
    
    cache(resourceType).asInstanceOf[Dataset[T]]
  }
}