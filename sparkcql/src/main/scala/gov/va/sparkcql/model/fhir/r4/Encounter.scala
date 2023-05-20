package gov.va.sparkcql.model.fhir.r4

trait EncounterLike extends ResourceLike {
  val identifier: Option[List[Identifier]]
  val status: Option[Code]
}

final case class Encounter (
  id: Id,
  status: Option[Code] = None,
  meta: Option[Meta] = None,
  implicitRules: Option[Uri] = None,
  language: Option[Code] = None,
  identifier: Option[List[Identifier]] = None  
) extends EncounterLike