package gov.va.sparkcql.model.fhir

import gov.va.sparkcql.model.fhir.Primitive._

trait ConditionLike extends ResourceLike {
  def identifier: Option[List[Identifier]]
  def clinicalStatus: CodeableConcept
  def verificationStatus: Option[CodeableConcept]
  def category: Option[List[CodeableConcept]]
  def severity: Option[CodeableConcept]
  def code: Option[CodeableConcept]
  def bodySite: Option[List[CodeableConcept]]
}

final case class Condition (
  id: Id,
  meta: Option[Meta] = None,
  implicitRules: Option[Uri] = None,
  language: Option[Code] = None,
  identifier: Option[List[Identifier]] = None,
  clinicalStatus: CodeableConcept,
  verificationStatus: Option[CodeableConcept] = None,
  category: Option[List[CodeableConcept]] = None,
  severity: Option[CodeableConcept] = None,
  code: Option[CodeableConcept] = None,
  bodySite: Option[List[CodeableConcept]] = None
) extends ConditionLike