package gov.va.sparkcql.model.fhir.r4

import gov.va.sparkcql.model.fhir.FhirClinicalData

trait BaseLike extends FhirClinicalData

final case class Base() extends BaseLike