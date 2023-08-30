package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;

public class SparkIndexedDataRetrieverFactory extends RetrieverFactory {

    public SparkIndexedDataRetrieverFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Retriever create(SparkFactory sparkFactory) {
        var injector = new Injector(getConfiguration());
        var tableResolutionStrategy = injector.getInstance(TableResolutionStrategyFactory.class).create();
        return new SparkIndexedDataRetriever(sparkFactory, tableResolutionStrategy);
    }
}
