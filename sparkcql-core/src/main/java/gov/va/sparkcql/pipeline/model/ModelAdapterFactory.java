package gov.va.sparkcql.pipeline.model;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.ComponentFactory;

public abstract class ModelAdapterFactory extends ComponentFactory {

    public ModelAdapterFactory(Configuration configuration) {
        super(configuration);
    }

    public abstract ModelAdapter create();
}
