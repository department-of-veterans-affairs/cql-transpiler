package gov.va.sparkcql.binding

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import gov.va.sparkcql.model.fhir._
import gov.va.sparkcql.model.fhir.Primitive._
import java.nio.file.{FileSystems, Files}
import scala.collection.JavaConverters._
import org.apache.spark.sql.Encoders
import scala.collection.mutable.HashMap
import org.apache.spark.sql.Dataset
import scala.reflect.runtime.universe._
import org.apache.spark.sql.types._

class BundleFolderBinding(spark: SparkSession, path: String) extends Binding(spark) {

  var dfCache = HashMap[Coding, Object]()

  // Lazy loading and initialization of bundles.
  lazy val dsEntry = {
    // Load all files for given path into a Seq of strings.
    val folder = new java.io.File(path)
    val files = folder.listFiles()
    val jsonBundle = files.map(f => {
      scala.io.Source.fromFile(f).mkString
    }).toSeq

    // Mount the bundles into Spark using the correct Bundle schema.
    val schema = Encoders.product[Bundle].schema
    schema.printTreeString()
    println(schema.fields(8).name)
    val newSchema = StructType(
      List(
        StructField("resourceType", StringType),
        StructField("type", StringType),
        StructField("entry", ArrayType(StringType))
      )
    )

    import spark.implicits._
    val dsBundle = spark.read.schema(newSchema).json(jsonBundle.toDS).as[Bundle]
    
    // Explode down to the entry (instance) level
    dsBundle.flatMap(_.entry)
  }

  def query[T <: Product : TypeTag](resourceType: Coding, code: Option[Coding] = None, startDate: Option[DateTime] = None, endDate: Option[DateTime] = None): Dataset[T] = {
    // Is the given resource type in our cache?
    if (!dfCache.contains(resourceType)) {
      import spark.implicits._

      // Load it now from the already loaded bundles and store it in the cache as a Dataset[T]
      // val ds = dsEntry.filter(_.resource.resourceType.get == resourceType.code).select("resource.*").as[AnyResource]
      import spark.implicits._
      //val schema = Encoders.product[AnyResource].schema
      val ds = dsEntry.where(expr("resource.resourceType").equalTo(resourceType.code)).as[Encounter]
      println(ds.head().id)

      // .select(expr("TO_JSON(entry.resource)"))
      // .where(expr("entry.resource.resourceType").equalTo(resourceType.code))
      
      dfCache.put(resourceType, ds)
    }

    dfCache(resourceType).asInstanceOf[Dataset[T]]
  }
}