package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait Encounterable extends Resourceable {
  def identifier: Option[List[Identifier]]
  def status: Option[Code]
}

final case class Encounter (
  id: Id,
  status: Option[Code] = None,
  resourceType: Option[String] = None,
  meta: Option[Meta] = None,
  implicitRules: Option[Uri] = None,
  language: Option[Code] = None,
  identifier: Option[List[Identifier]] = None  
) extends Encounterable