package gov.va.sparkcql.fhir

import gov.va.sparkcql.model.Model
import scala.reflect.runtime.universe._
import org.apache.spark.sql.types.StructType
import javax.xml.namespace.QName

class FhirModel extends Model {

  override val namespaceUri: String = "http://hl7.org/fhir"
  
  override val supportedDataTypes: List[QName] = {
    List(
      toDataType("AllergyIntolerance"),
      toDataType("CarePlan"),
      toDataType("CareTeam"),
      toDataType("Claim"),
      toDataType("Condition"),
      toDataType("Coverage"),
      toDataType("Device"),
      toDataType("DiagnosticReport"),
      toDataType("Encounter"),
      toDataType("Goal"),
      toDataType("ImagingStudy"),
      toDataType("Immunization"),
      toDataType("Location"),
      toDataType("MedicationRequest"),
      toDataType("Observation"),
      toDataType("Organization"),
      toDataType("Patient"),
      toDataType("Practitioner"),
      toDataType("Procedure")
    )
  }

  override def schemaOf(dataType: QName): Option[StructType] = {
    None
  }  

  override def metaInterval(typeName: String): (String, String) = {
    typeName.toLowerCase match {
      case "period" => ("start", "end")
    }
  }

  override def typeToElmMapping(typeName: String): Map[String, String] = {
    typeName.toLowerCase match {
      case "period" => Map("start" -> "low", "end" -> "high")
    }
  }
}