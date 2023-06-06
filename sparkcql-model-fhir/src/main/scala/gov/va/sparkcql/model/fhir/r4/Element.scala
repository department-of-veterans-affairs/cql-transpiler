package gov.va.sparkcql.model.fhir.r4

import gov.va.sparkcql.model.fhir.primitives._

trait ElementLike extends BaseLike {
  val id: Option[String]
}

final case class Element (
  id: Option[String] = None
) extends ElementLike