package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.ComponentFactory;

public interface PreprocessorFactory extends ComponentFactory {

    public abstract Preprocessor create(Configuration configuration, SparkFactory sparkFactory, ModelAdapterSet modelAdapterSet);
}