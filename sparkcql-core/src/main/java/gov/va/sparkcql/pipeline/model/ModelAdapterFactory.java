package gov.va.sparkcql.pipeline.model;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.ComponentFactory;

public interface ModelAdapterFactory extends ComponentFactory {

    public ModelAdapter create(Configuration configuration);
}
