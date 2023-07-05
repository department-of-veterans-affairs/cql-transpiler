package gov.va.sparkcql.synthea

import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._
import scala.collection.mutable.HashMap
import javax.xml.namespace.QName
import gov.va.sparkcql.adapter.data.DataAdapter
import gov.va.sparkcql.logging.Log

class SyntheaDataAdapter(val spark: SparkSession, size: SampleSize) extends DataAdapter {

  import spark.implicits._

  val TablePrefix = "Synthea"
  val DefaultResourceColumnName = "resource_data"
  val DefaultResourceTypeColumnName = "resource_type"
  val BundlesResourceType = "Bundles"

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

  def getResourceTypeAsDataFrame(resourceType: String): DataFrame = {
    dfCache.getOrElseUpdate(resourceType, {
      val resourceText = dfBundles
        .where(col(DefaultResourceTypeColumnName).equalTo(resourceType))
        .select(col(DefaultResourceColumnName))
        .toDF()
      spark.read.json(resourceText.as[String])
    })
  }
  
  def isDataTypeDefined(dataType: QName): Boolean = {
    dataType.toString() match {
        // Synthea Supported Types
        case "{http://hl7.org/fhir}AllergyIntolerance" => true
        case "{http://hl7.org/fhir}CarePlan" => true
        case "{http://hl7.org/fhir}CareTeam" => true
        case "{http://hl7.org/fhir}Claim" => true
        case "{http://hl7.org/fhir}Condition" => true
        case "{http://hl7.org/fhir}Coverage" => true
        case "{http://hl7.org/fhir}Device" => true
        case "{http://hl7.org/fhir}DiagnosticReport" => true
        case "{http://hl7.org/fhir}Encounter" => true
        case "{http://hl7.org/fhir}Goal" => true
        case "{http://hl7.org/fhir}ImagingStudy" => true
        case "{http://hl7.org/fhir}Immunization" => true
        case "{http://hl7.org/fhir}Location" => true
        case "{http://hl7.org/fhir}MedicationRequest" => true
        case "{http://hl7.org/fhir}Observation" => true
        case "{http://hl7.org/fhir}Organization" => true
        case "{http://hl7.org/fhir}Patient" => true
        case "{http://hl7.org/fhir}Practitioner" => true
        case "{http://hl7.org/fhir}Procedure" => true
        case _ => false
    }
  }

  def acquire(dataType: QName): Option[DataFrame] = {
    if (size != SampleSize.SampleSizeNone && isDataTypeDefined(dataType)) {
      val resourceType = dataType.getLocalPart()
      Some(getResourceTypeAsDataFrame(resourceType))
    } else {
      None
    }
  }
}