package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait CodeableConceptLike extends ElementLike {
  def coding: List[Coding]
  def text: Option[String]
}

final case class CodeableConcept (
  coding: List[Coding],
  text: Option[String] = None,
  id: Option[String] = None
) extends CodeableConceptLike