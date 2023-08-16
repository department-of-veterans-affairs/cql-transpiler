package gov.va.sparkcql.repository.clinical;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.repository.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public abstract class FhirDataRepository<T> extends SparkClinicalRepository<T> {

    @Inject
    public FhirDataRepository(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    public DataType getEntityDataType() {
        return new DataType()
            .withNamespaceUri("http://hl7.org/fhir")
            .withName(this.getEntityClass().getSimpleName());
    }
}