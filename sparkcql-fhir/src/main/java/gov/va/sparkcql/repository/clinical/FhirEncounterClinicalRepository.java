package gov.va.sparkcql.repository.clinical;

import org.hl7.fhir.r4.model.Encounter;

import gov.va.sparkcql.pipeline.repository.clinical.ClinicalRepository;

public interface FhirEncounterClinicalRepository extends ClinicalRepository<Encounter> {
}
