package gov.va.sparkcql.model.fhir.r4

trait CodeableConceptLike extends ElementLike {
  val coding: List[Coding]
  val text: Option[String]
}

final case class CodeableConcept (
  coding: List[Coding],
  text: Option[String] = None,
  id: Option[String] = None
) extends CodeableConceptLike