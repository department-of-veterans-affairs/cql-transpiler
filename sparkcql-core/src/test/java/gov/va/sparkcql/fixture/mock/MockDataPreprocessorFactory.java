package gov.va.sparkcql.fixture.mock;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.preprocessor.Preprocessor;
import gov.va.sparkcql.pipeline.preprocessor.PreprocessorFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;

public class MockDataPreprocessorFactory extends PreprocessorFactory {

    public MockDataPreprocessorFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Preprocessor create(SparkFactory sparkFactory) {
        var tableResolutionStrategy = new Injector(getConfiguration())
                .getInstance(TableResolutionStrategyFactory.class)
                .create();
        return new MockDataPreprocessor(sparkFactory, tableResolutionStrategy);
    }
}