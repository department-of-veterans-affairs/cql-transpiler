package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;

public class SparkIndexedDataRetrieverFactory implements RetrieverFactory {

    @Override
    public Retriever create(Configuration configuration, SparkFactory sparkFactory) {
        var tableResolutionStrategyFactory = new Injector(configuration).getInstance(TableResolutionStrategyFactory.class);
        return new SparkIndexedDataRetriever(
                configuration,
                sparkFactory,
                tableResolutionStrategyFactory.create(configuration));
    }
}
