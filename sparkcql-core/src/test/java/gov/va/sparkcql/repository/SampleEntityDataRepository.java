package gov.va.sparkcql.repository;

import com.google.inject.Inject;

import gov.va.sparkcql.common.spark.SparkFactory;
import gov.va.sparkcql.entity.DataType;
import gov.va.sparkcql.entity.SampleEntity;

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