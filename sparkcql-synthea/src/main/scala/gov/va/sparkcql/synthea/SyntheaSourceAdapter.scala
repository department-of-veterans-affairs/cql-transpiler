package gov.va.sparkcql.synthea

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row, Encoders}
import org.apache.spark.sql.functions._
import scala.collection.mutable.HashMap
import gov.va.sparkcql.core.adapter.model.ModelAdapter
import gov.va.sparkcql.core.model.DataType
import gov.va.sparkcql.core.adapter.source.SourceAdapter

class SyntheaSourceAdapter(spark: SparkSession, size: PopulationSize, modelAdapter: ModelAdapter) extends SourceAdapter(spark, modelAdapter) {

  import spark.implicits._

  val TablePrefix = "Synthea"
  val DefaultResourceColumnName = "resource_data"
  val BundlesResourceType = "Bundles"

  val dfCache = HashMap[String, Option[Dataset[Row]]]()

  lazy val bundles = {
    SyntheaDataLoader.loadBundles(size)
  }

  lazy val dfBundles = {
    spark.read.json(bundles.toDS)
      .select(explode(col("entry")).as("entry"))
      .select(
        to_json(struct(col("entry.resource.*"))).as(DefaultResourceColumnName),
        col("entry.resource.resourceType").as("resourceType"))
  }

  def createDataSet(resourceType: String): Option[Dataset[Row]] = {
    dfCache.getOrElseUpdate(resourceType, {
      val resourceText = dfBundles
        .where(col("resourceType").equalTo(resourceType))
        .select(col(DefaultResourceColumnName))
        .toDF()

      if (resourceText.count() > 0) {
        Some(spark.read.json(resourceText.as[String]))
      } else {
        None
      }
    })
  }

  def read(dataType: DataType): Option[Dataset[Row]] = {
    // Quick check to avoid eager loading when the requested type clearly isn't supported.
    if (dataType.system.toLowerCase() == "http://hl7.org/fhir") {
      val resourceType = dataType.name
      val df = createDataSet(resourceType)
      df
    } else {
      None
    }
  }
}