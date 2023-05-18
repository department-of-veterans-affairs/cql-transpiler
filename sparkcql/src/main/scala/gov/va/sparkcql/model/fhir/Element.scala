package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait ElementLike extends BaseLike {
  def id: Option[String]
}

final case class Element (
  id: Option[String] = None
) extends ElementLike