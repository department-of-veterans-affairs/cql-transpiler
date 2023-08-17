package gov.va.sparkcql.repository.clinical;

import org.hl7.fhir.r4.model.Condition;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.repository.resolution.TableResolutionStrategy;

public class FhirConditionSparkRepository extends FhirSparkRepository<Condition> implements FhirConditionClinicalRepository {

    @Inject
    public FhirConditionSparkRepository(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    public Class<Condition> getEntityClass() {
        return Condition.class;
    }
}