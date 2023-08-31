package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.pipeline.model.ModelAdapterCollection;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;

public class FhirSyntheticDataPreprocessorFactory extends PreprocessorFactory {

    public FhirSyntheticDataPreprocessorFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Preprocessor create(SparkFactory sparkFactory, ModelAdapterCollection modelAdapterCollection) {
        var tableResolutionStrategy = new Injector(getConfiguration())
                .getInstance(TableResolutionStrategyFactory.class)
                .create();
        return new FhirSyntheticDataPreprocessor(sparkFactory, tableResolutionStrategy, modelAdapterCollection);
    }
}