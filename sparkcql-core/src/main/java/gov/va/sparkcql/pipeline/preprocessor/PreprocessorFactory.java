package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.configuration.ComponentFactory;

public abstract class PreprocessorFactory extends ComponentFactory {

    public PreprocessorFactory(Configuration configuration) {
        super(configuration);
    }

    public abstract Preprocessor create(SparkFactory sparkFactory, ModelAdapterSet modelAdapterSet);
}