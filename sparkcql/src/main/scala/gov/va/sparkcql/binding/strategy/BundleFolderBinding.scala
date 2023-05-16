package gov.va.sparkcql.binding.strategy

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import gov.va.sparkcql.model.fhir._
import gov.va.sparkcql.model.fhir.Primitive._
import java.nio.file.{FileSystems, Files}
import scala.collection.JavaConverters._
import org.apache.spark.sql.Encoders
import org.apache.arrow.flatbuf.Bool
import scala.collection.mutable.HashMap

class BundleFolderBinding(path: String) extends BindingStrategy {

  var dfEntry: Option[DataFrame] = None
  var dfResourceType = HashMap[Coding, DataFrame]()

  /**
    * Resource types are lazy loaded.
    *
    * @param spark
    * @param resourceType
    */
  def ensureMounted(spark: SparkSession, resourceType: Coding): Unit = {
    // Ensures the bundles are loaded and exploded down to the entry (instance) level.
    if (dfEntry.isEmpty) {
      // Load all files for given path into a Seq of strings.
      val folder = new java.io.File(path)
      val files = folder.listFiles()
      val jsonBundle = files.map(f => {
        scala.io.Source.fromFile(f).mkString
      }).toSeq

      // Mount as a Bundles table
      import spark.implicits._
      val dfBundle = spark.read.json(jsonBundle.toDS)
      dfEntry = Some(dfBundle.select(explode(col("entry")).as("entry")))
    }

    // Ensures the given resource type is mounted by filtering all entries by the given type.
    if (!dfResourceType.contains(resourceType)) {
        dfResourceType += resourceType -> dfEntry.get
            .withColumn("Resource", expr("STRUCT(*)"))
            .drop("entry")
            .where(expr("entry.resource.resourceType").equalTo(resourceType.code))
    }
  }

  def resolve(spark: SparkSession, resourceType: Coding, code: Option[Coding] = None, startDate: Option[DateTime] = None, endDate: Option[DateTime] = None): DataFrame = {
    this.ensureMounted(spark, resourceType)
    dfResourceType(resourceType)
  }
}