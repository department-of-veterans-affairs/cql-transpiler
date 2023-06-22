package gov.va.sparkcql.fhir

import gov.va.sparkcql.core.model.Model
import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType
import javax.xml.namespace.QName

class FhirModel extends Model {

  override val namespaceUri: String = "http://hl7.org/fhir"
  
  override val supportedDataTypes: List[QName] = {
    List(
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
    )
  }

  override def schemaOf(dataType: QName): Option[StructType] = {
    None
  }  
}