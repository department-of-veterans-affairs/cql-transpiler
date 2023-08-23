package gov.va.sparkcql.repository.clinical;

import org.hl7.fhir.r4.model.Patient;

import gov.va.sparkcql.pipeline.repository.clinical.ClinicalRepository;

public interface FhirPatientClinicalRepository extends ClinicalRepository<Patient> {
}