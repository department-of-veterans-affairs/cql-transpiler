package gov.va.sparkcql.repository;

import org.hl7.fhir.r4.model.Encounter;

import gov.va.sparkcql.configuration.SparkFactory;

public class SyntheaFhirEncounterRepository extends SyntheaFhirSparkRepository<Encounter> {

    public SyntheaFhirEncounterRepository(SparkFactory sparkFactory, SyntheaTableResolutionStrategy resolutionStrategy,
            SyntheticPopulationSize size) {
        super(sparkFactory, resolutionStrategy, size);
    }
    
}
