package gov.va.sparkcql.repository.clinical;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.domain.SampleDomain;
import gov.va.sparkcql.repository.resolution.TableResolutionStrategy;
import gov.va.sparkcql.types.DataType;

public class SamplePatientClinicalRepository extends SampleClinicalRepository<SampleDomain> {

    @Inject
    public SamplePatientClinicalRepository(SparkFactory sparkFactory, TableResolutionStrategy tableResolutionStrategy) {
        super(sparkFactory, tableResolutionStrategy);
    }

    @Override
    protected String getJsonDataPath() {
        return "sample/sample-data-patient.json";
    }

    @Override
    public DataType getEntityDataType() {
        return new DataType()
            .withNamespaceUri("http://va.gov/sparkcql/sample")
            .withName("Patient");
    }
}