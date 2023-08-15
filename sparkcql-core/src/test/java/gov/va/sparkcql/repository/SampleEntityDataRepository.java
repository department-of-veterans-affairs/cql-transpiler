package gov.va.sparkcql.repository;

import javax.xml.namespace.QName;

import com.google.inject.Inject;

import gov.va.sparkcql.common.spark.SparkFactory;
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
    public QName getEntityDataType() {
        return new QName("http://gov.va/sparkcql/sample", "Entity");
    }
}