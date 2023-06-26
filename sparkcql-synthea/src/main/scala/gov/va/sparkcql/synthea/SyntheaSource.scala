package gov.va.sparkcql.synthea

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._
import scala.collection.mutable.HashMap
import javax.xml.namespace.QName
import gov.va.sparkcql.model.Model
import gov.va.sparkcql.source.Source

class SyntheaSource(val models: List[Model], val spark: SparkSession, size: PopulationSize) extends Source {

  import spark.implicits._

  val TablePrefix = "Synthea"
  val DefaultResourceColumnName = "resource_data"
  val BundlesResourceType = "Bundles"

  val dfCache = HashMap[String, Option[DataFrame]]()

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

  def createDataFrame(resourceType: String): Option[DataFrame] = {
    dfCache.getOrElseUpdate(resourceType, {
      val resourceText = dfBundles
        .where(col("resourceType").equalTo(resourceType))
        .select(col(DefaultResourceColumnName))
        .toDF()

      if (resourceText.schema.fields.length > 0) {
        Some(spark.read.json(resourceText.as[String]))
      } else {
        None
      }
    })
  }
  
  def isDataTypePresent(dataType: QName): Boolean = {
    if (dataType.getNamespaceURI.toLowerCase() == "http://hl7.org/fhir") {
      true
    } else {
      false
    }
  }

  def acquireData(dataType: QName): Option[DataFrame] = {
    val resourceType = dataType.getLocalPart()
    createDataFrame(resourceType)
  }
}