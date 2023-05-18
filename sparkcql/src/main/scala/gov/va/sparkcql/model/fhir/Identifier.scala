package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait IdentifierLike extends ElementLike {
  def system: String
  def value: String
  def use: Option[String]
  def `type`: Option[CodeableConcept]
  def period: Option[Object]
}

final case class Identifier (
  system: String,
  value: String,
  use: Option[String] = None,
  `type`: Option[CodeableConcept] = None,
  period: Option[String] = None,
  id: Option[String] = None
) extends IdentifierLike