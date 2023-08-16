package gov.va.sparkcql.repository;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.repository.clinical.SparkClinicalRepository;

public abstract class FhirDataRepository<T> extends SparkClinicalRepository<T> {

    public FhirDataRepository(SparkFactory sparkFactory) {
        super(sparkFactory);
    }
}