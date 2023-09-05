package gov.va.sparkcql.mock;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.preprocessor.Preprocessor;
import gov.va.sparkcql.pipeline.preprocessor.PreprocessorFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;

public class MockDataPreprocessorFactory implements PreprocessorFactory {

    @Override
    public Preprocessor create(Configuration configuration, SparkFactory sparkFactory, ModelAdapterSet modelAdapterSet) {
        var tableResolutionStrategy = new Injector(configuration)
                .getInstance(TableResolutionStrategyFactory.class)
                .create(configuration);
        return new MockDataPreprocessor(configuration, sparkFactory, tableResolutionStrategy, modelAdapterSet);
    }
}