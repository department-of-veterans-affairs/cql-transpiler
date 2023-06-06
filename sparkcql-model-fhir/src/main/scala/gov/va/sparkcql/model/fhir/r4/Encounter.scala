package gov.va.sparkcql.model.fhir.r4

import gov.va.sparkcql.model.fhir.primitives._

trait EncounterLike extends ResourceLike {
  val status: Option[Code]
}

final case class Encounter (
  id: Id,
  status: Option[Code] = None
) extends EncounterLike