package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.pipeline.ComponentFactory;

public abstract class PreprocessorFactory extends ComponentFactory {

    public PreprocessorFactory(Configuration configuration) {
        super(configuration);
    }

    public abstract Preprocessor create(SparkFactory sparkFactory);
}