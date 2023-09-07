package gov.va.sparkcql.pipeline.repository.cql;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.ComponentFactory;

public interface CqlSourceRepositoryFactory extends ComponentFactory {

    public abstract CqlSourceRepository create(Configuration configuration, SparkFactory sparkFactory);
}