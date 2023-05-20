package gov.va.sparkcql.model.fhir.r4

trait ElementLike extends BaseLike {
  val id: Option[String]
}

final case class Element (
  id: Option[String] = None
) extends ElementLike