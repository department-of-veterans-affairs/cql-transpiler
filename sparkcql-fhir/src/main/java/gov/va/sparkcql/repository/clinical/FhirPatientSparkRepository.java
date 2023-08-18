package gov.va.sparkcql.repository.clinical;

import org.hl7.fhir.r4.model.Encounter;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.repository.resolution.TableResolutionStrategy;

public class FhirEncounterSparkRepository extends FhirSparkRepository<Encounter> implements FhirEncounterClinicalRepository {

    @Inject
    public FhirEncounterSparkRepository(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    public Class<Encounter> getEntityClass() {
        return Encounter.class;
    }
}