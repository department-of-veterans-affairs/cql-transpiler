package gov.va.sparkcql.fhir

import gov.va.sparkcql.adapter.model.ModelAdapter
import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType
import javax.xml.namespace.QName
import au.csiro.pathling.encoders.{EncoderConfig, SchemaConverter}
import au.csiro.pathling.encoders.datatypes.R4DataTypeMappings
import ca.uhn.fhir.context.FhirContext

class FhirModelAdapter extends ModelAdapter {

  private val fhirContext = FhirContext.forR4()
  private val dataTypeMappings = new R4DataTypeMappings()
  private val maxNestingLevel = 5
      
  override val namespaceUri: String = "http://hl7.org/fhir"
  
  override val supportedDataTypes: List[QName] = {
    List(
        new QName(namespaceUri, "AllergyIntolerance"),
        new QName(namespaceUri, "CarePlan"),
        new QName(namespaceUri, "CareTeam"),
        new QName(namespaceUri, "Claim"),
        new QName(namespaceUri, "Condition"),
        new QName(namespaceUri, "Coverage"),
        new QName(namespaceUri, "Device"),
        new QName(namespaceUri, "DiagnosticReport"),
        new QName(namespaceUri, "Encounter"),
        new QName(namespaceUri, "Goal"),
        new QName(namespaceUri, "ImagingStudy"),
        new QName(namespaceUri, "Immunization"),
        new QName(namespaceUri, "Location"),
        new QName(namespaceUri, "MedicationRequest"),
        new QName(namespaceUri, "Observation"),
        new QName(namespaceUri, "Organization"),
        new QName(namespaceUri, "Patient"),
        new QName(namespaceUri, "Practitioner"),
        new QName(namespaceUri, "Procedure")
    )
  }

  override def schemaOf(dataType: QName): Option[StructType] = {
    assertDataTypeIsSupported(dataType)
    val schemaConverter = new SchemaConverter(fhirContext, dataTypeMappings, EncoderConfig(maxNestingLevel, null, false))
    val clazz = dataType.getLocalPart() match {
      case "AllergyIntolerance" => classOf[org.hl7.fhir.r4.model.AllergyIntolerance]
      case "CarePlan" => classOf[org.hl7.fhir.r4.model.CarePlan]
      case "CareTeam" => classOf[org.hl7.fhir.r4.model.CareTeam]
      case "Claim" => classOf[org.hl7.fhir.r4.model.Claim]
      case "Condition" => classOf[org.hl7.fhir.r4.model.Condition]
      case "Coverage" => classOf[org.hl7.fhir.r4.model.Coverage]
      case "Device" => classOf[org.hl7.fhir.r4.model.Device]
      case "DiagnosticReport" => classOf[org.hl7.fhir.r4.model.DiagnosticReport]
      case "Encounter" => classOf[org.hl7.fhir.r4.model.Encounter]
      case "Goal" => classOf[org.hl7.fhir.r4.model.Goal]
      case "ImagingStudy" => classOf[org.hl7.fhir.r4.model.ImagingStudy]
      case "Immunization" => classOf[org.hl7.fhir.r4.model.Immunization]
      case "Location" => classOf[org.hl7.fhir.r4.model.Location]
      case "MedicationRequest" => classOf[org.hl7.fhir.r4.model.MedicationRequest]
      case "Observation" => classOf[org.hl7.fhir.r4.model.Observation]
      case "Organization" => classOf[org.hl7.fhir.r4.model.Organization]
      case "Patient" => classOf[org.hl7.fhir.r4.model.Patient]
      case "Practitioner" => classOf[org.hl7.fhir.r4.model.Practitioner]
      case "Procedure" => classOf[org.hl7.fhir.r4.model.Procedure]
      case _ => throw new RuntimeException(s"Unexpected data type '${dataType.toString()}'.")
    }
    val schema = schemaConverter.resourceSchema(clazz)
    Some(schema)
  }  

  override def intervalBoundTerms(): (String, String) = ("start", "end")
}