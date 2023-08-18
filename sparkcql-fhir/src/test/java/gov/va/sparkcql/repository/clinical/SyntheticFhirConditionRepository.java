package gov.va.sparkcql.repository.clinical;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;

public class SyntheticFhirConditionRepository extends FhirConditionSparkRepository {

    @Inject
    public SyntheticFhirConditionRepository(SparkFactory sparkFactory) {
        super(sparkFactory, new SyntheticTableResolutionStrategy());
        SyntheticMount.mountData(spark, this, this.tableResolutionStrategy);
    }
}