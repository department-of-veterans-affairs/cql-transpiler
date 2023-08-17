package gov.va.sparkcql.repository.clinical;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;

public class SyntheticFhirEncounterRepository extends FhirEncounterSparkRepository {

    @Inject
    public SyntheticFhirEncounterRepository(SparkFactory sparkFactory) {
        super(sparkFactory, new SyntheticTableResolutionStrategy());
        SyntheticMount.mountData(spark, this, this.tableResolutionStrategy);
    }
}