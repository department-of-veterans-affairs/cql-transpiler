package gov.va.sparkcql.pipeline.repository.cql;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.ComponentFactory;

public abstract class CqlSourceRepositoryFactory extends ComponentFactory {

    public CqlSourceRepositoryFactory(Configuration configuration) {
        super(configuration);
    }

    public abstract CqlSourceRepository create(SparkFactory sparkFactory);
}