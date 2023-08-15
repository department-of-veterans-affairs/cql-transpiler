package gov.va.sparkcql.repository;

import javax.xml.namespace.QName;

import com.google.inject.Inject;

import gov.va.sparkcql.common.spark.SparkFactory;
import gov.va.sparkcql.entity.SampleEntity;

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
    public QName getEntityDataType() {
        return new QName("http://gov.va/sparkcql/sample", "Patient");
    }
}