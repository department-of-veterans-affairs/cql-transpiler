package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.configuration.ComponentFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;

public abstract class RetrieverFactory extends ComponentFactory {

    public RetrieverFactory(Configuration configuration) {
        super(configuration);
        var tableResolutionStrategy = new Injector(configuration).getInstance(TableResolutionStrategyFactory.class);
    }

    public abstract Retriever create(SparkFactory sparkFactory);
}