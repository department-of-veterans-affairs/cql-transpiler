package gov.va.sparkcql.fhir

import gov.va.sparkcql.core.adapter.model.{ModelAdapter, ModelCapabilityStatement}
import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType
import javax.xml.namespace.QName

class FhirModelAdapter extends ModelAdapter {

  def namespaceUri(): String = "http://hl7.org/fhir"
  
  override def stateCapabilities(): ModelCapabilityStatement = {
    ModelCapabilityStatement(true, true, Some(List(
      makeLocalDataType("AllergyIntolerance"),
      makeLocalDataType("CarePlan"),
      makeLocalDataType("CareTeam"),
      makeLocalDataType("Claim"),
      makeLocalDataType("Condition"),
      makeLocalDataType("Coverage"),
      makeLocalDataType("Device"),
      makeLocalDataType("DiagnosticReport"),
      makeLocalDataType("Encounter"),
      makeLocalDataType("Goal"),
      makeLocalDataType("ImagingStudy"),
      makeLocalDataType("Immunization"),
      makeLocalDataType("Location"),
      makeLocalDataType("MedicationRequest"),
      makeLocalDataType("Observation"),
      makeLocalDataType("Organization"),
      makeLocalDataType("Patient"),
      makeLocalDataType("Practitioner"),
      makeLocalDataType("Procedure")
    )))
  }

  override def schemaOf(dataType: QName): Option[StructType] = {
    None
  }  
}