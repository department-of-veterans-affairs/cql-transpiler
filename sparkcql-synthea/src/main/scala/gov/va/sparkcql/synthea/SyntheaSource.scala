package gov.va.sparkcql.synthea

import scala.reflect.runtime.universe._
import org.apache.spark.sql.{SparkSession, Dataset, Row}
import org.apache.spark.sql.functions._
import scala.collection.mutable.HashMap
import javax.xml.namespace.QName
import gov.va.sparkcql.core.model.Model
import gov.va.sparkcql.core.source.Source

class SyntheaSource(val models: List[Model], val spark: SparkSession, size: PopulationSize) extends Source {

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

  def acquireData(dataType: QName): Option[Dataset[Row]] = {
    val resourceType = dataType.getLocalPart()
    createDataSet(resourceType)
  }
}