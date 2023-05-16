package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

final case class Coding(system: Uri, code: Code, version: Option[String] = None, display: Option[String] = None, userSelected: Option[String] = None) {
}