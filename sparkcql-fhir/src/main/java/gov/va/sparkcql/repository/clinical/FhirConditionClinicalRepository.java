package gov.va.sparkcql.repository.clinical;

import org.hl7.fhir.r4.model.Condition;

import gov.va.sparkcql.pipeline.repository.clinical.ClinicalRepository;

public interface FhirConditionClinicalRepository extends ClinicalRepository<Condition> {
}
