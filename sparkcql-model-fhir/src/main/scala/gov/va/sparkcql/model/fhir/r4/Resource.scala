package gov.va.sparkcql.model.fhir.r4

import gov.va.sparkcql.model.fhir.primitives._

trait ResourceLike extends BaseLike {
  val id: Id
 }

final case class Resource (
  id: Id
) extends ResourceLike