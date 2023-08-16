package gov.va.sparkcql.repository;

import gov.va.sparkcql.common.spark.SparkFactory;

public abstract class FhirDataRepository<T> extends SparkClinicalDataRepository<T> {

    public FhirDataRepository(SparkFactory sparkFactory) {
        super(sparkFactory);
    }
}