package gov.va.sparkcql.repository;

import javax.xml.namespace.QName;

import com.google.inject.Inject;

import gov.va.sparkcql.common.spark.SparkFactory;

public class SampleDataRepositoryFactory implements ClinicalDataRepositoryFactory {

    @Inject
    public SampleDataRepositoryFactory(SparkFactory sparkFactory) {
        this.sparkFactory = sparkFactory;
    }

    SparkFactory sparkFactory;

    @Override
    public ClinicalDataRepository<?> create(QName dataType) {
        switch (dataType.toString()) {
            case "{http://gov.va/sparkcql/sample}Patient": return new SamplePatientDataRepository(sparkFactory);
            case "{http://gov.va/sparkcql/sample}Entity": return new SampleEntityDataRepository(sparkFactory);
            default: return null;
        }
    }
}