package gov.va.sparkcql.repository.clinical;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.domain.SampleEntity;
import gov.va.sparkcql.types.DataType;

public class SampleEntityDataRepository extends SampleDataRepository<SampleEntity> {

    @Inject
    public SampleEntityDataRepository(SparkFactory sparkFactory) {
        super(sparkFactory);
    }

    @Override
    protected String getJsonDataPath() {
        return "sample/sample-data-entity.json";
    }

    @Override
    public DataType getEntityDataType() {
        return new DataType()
            .withNamespaceUri("http://va.gov/sparkcql/sample")
            .withName("Entity");
    }
}