package gov.va.sparkcql.pipeline.repository.cql;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.runtime.SparkFactory;

public class CqlSourceNullRepositoryFactory implements CqlSourceRepositoryFactory {

    @Override
    public CqlSourceRepository create(Configuration configuration, SparkFactory sparkFactory) {
        return new CqlSourceNullRepository();
    }
}
