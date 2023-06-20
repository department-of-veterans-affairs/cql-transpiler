package gov.va.sparkcql.fhir

import javax.xml.namespace.QName

object FhirDataType {
    
  def apply(resourceType: String): QName = new QName("http://hl7.org/fhir", resourceType)

  def apply(resourceType: String, version: String): QName = new QName("http://hl7.org/fhir", resourceType, version)
}