package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.pipeline.ComponentFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;

public abstract class RetrieverFactory extends ComponentFactory {

    public RetrieverFactory(Configuration configuration) {
        super(configuration);
        var tableResolutionStrategy = new Injector(configuration).getInstance(TableResolutionStrategyFactory.class);
    }

    public abstract Retriever create(SparkFactory sparkFactory);
}