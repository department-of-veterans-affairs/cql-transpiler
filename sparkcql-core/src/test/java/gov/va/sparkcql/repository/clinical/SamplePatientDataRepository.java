package gov.va.sparkcql.repository.clinical;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.domain.SampleEntity;
import gov.va.sparkcql.types.DataType;

public class SamplePatientDataRepository extends SampleDataRepository<SampleEntity> {

    @Inject
    public SamplePatientDataRepository(SparkFactory sparkFactory) {
        super(sparkFactory);
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