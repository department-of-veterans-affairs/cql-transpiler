package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.ClinicalDataLike

case class FhirClinicalData(dataType: String, version: Option[String]) extends ClinicalDataLike {
  override val system = s"http://hl7.org/fhir"
}