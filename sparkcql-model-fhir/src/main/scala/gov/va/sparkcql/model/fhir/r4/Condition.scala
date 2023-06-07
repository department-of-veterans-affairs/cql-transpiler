package gov.va.sparkcql.model.fhir.r4

import gov.va.sparkcql.model.fhir.primitives._

trait ConditionLike extends ResourceLike {
  //val identifier: Option[List[Identifier]]
  //val clinicalStatus: CodeableConcept
  //val verificationStatus: Option[CodeableConcept]
  //val category: Option[List[CodeableConcept]]
  // val severity: Option[CodeableConcept]
  // val code: Option[CodeableConcept]
  // val bodySite: Option[List[CodeableConcept]]
}

final case class Condition (
  id: Id,
  //identifier: Option[List[Identifier]] = None,
  // clinicalStatus: CodeableConcept,
  //verificationStatus: Option[CodeableConcept] = None,
  //category: Option[List[CodeableConcept]] = None,
  // severity: Option[CodeableConcept] = None,
  // code: Option[CodeableConcept] = None,
  // bodySite: Option[List[CodeableConcept]] = None
) extends ConditionLike