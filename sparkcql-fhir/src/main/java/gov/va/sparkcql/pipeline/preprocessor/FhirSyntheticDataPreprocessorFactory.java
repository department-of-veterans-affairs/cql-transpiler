package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;

public class FhirSyntheticDataPreprocessorFactory implements PreprocessorFactory {

    @Override
    public Preprocessor create(Configuration configuration, SparkFactory sparkFactory, ModelAdapterSet modelAdapterSet) {
        var tableResolutionStrategy = new Injector(configuration)
                .getInstance(TableResolutionStrategyFactory.class)
                .create(configuration);

        return new FhirSyntheticDataPreprocessor(configuration, sparkFactory, tableResolutionStrategy, modelAdapterSet);
    }
}