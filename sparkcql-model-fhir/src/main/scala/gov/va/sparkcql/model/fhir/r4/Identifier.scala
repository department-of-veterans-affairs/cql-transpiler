package gov.va.sparkcql.model.fhir.r4

import gov.va.sparkcql.model.fhir.primitives._

trait IdentifierLike extends ElementLike {
  val system: String
  val value: String
  val use: Option[String]
  val `type`: Option[CodeableConcept]
  val period: Option[Object]
}

final case class Identifier (
  system: String,
  value: String,
  use: Option[String] = None,
  `type`: Option[CodeableConcept] = None,
  period: Option[String] = None,
  id: Option[String] = None
) extends IdentifierLike