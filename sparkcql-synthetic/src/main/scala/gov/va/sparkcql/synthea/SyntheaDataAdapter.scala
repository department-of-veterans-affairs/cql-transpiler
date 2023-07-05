package gov.va.sparkcql.synthea

import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._
import scala.collection.mutable.HashMap
import javax.xml.namespace.QName
import gov.va.sparkcql.adapter.data.DataAdapter
import gov.va.sparkcql.io.Log
import gov.va.sparkcql.fhir.FhirModelAdapter

class SyntheaDataAdapter(val spark: SparkSession, size: SampleSize) extends DataAdapter {

  import spark.implicits._

  val TablePrefix = "Synthea"
  val DefaultResourceColumnName = "resource_data"
  val DefaultResourceTypeColumnName = "resource_type"
  val BundlesResourceType = "Bundles"
  val fhirAdapter = new FhirModelAdapter()
  val dfCache = HashMap[String, DataFrame]()

  lazy val bundles = {
    SyntheaDataLoader.loadBundles(size)
  }

  lazy val dfBundles = {
    // TODO apply schema
    spark.read.json(bundles.toDS)
      .select(explode(col("entry")).as("entry"))
      .select(
        to_json(struct(col("entry.resource.*"))).as(DefaultResourceColumnName),
        col("entry.resource.resourceType").as(DefaultResourceTypeColumnName))
  }

  def getAsDataFrame(dataType: QName): DataFrame = {
    val resourceType = dataType.getLocalPart()
    dfCache.getOrElseUpdate(resourceType, {
      val resourceText = dfBundles
        .where(col(DefaultResourceTypeColumnName).equalTo(resourceType))
        .select(col(DefaultResourceColumnName))
        .toDF()
      var schema = fhirAdapter.schemaOf(dataType).getOrElse(
        throw new Exception(s"Unable to resolve schema for type ${dataType.toString()}"))
      spark.read.schema(schema).json(resourceText.as[String])
    })
  }
  
  def isDataTypeDefined(dataType: QName): Boolean = {
    fhirAdapter.supportedDataTypes.contains(dataType)
  }

  def acquire(dataType: QName): Option[DataFrame] = {
    if (size != SampleSize.SampleSizeNone && isDataTypeDefined(dataType)) {
      Some(getAsDataFrame(dataType))
    } else {
      None
    }
  }
}