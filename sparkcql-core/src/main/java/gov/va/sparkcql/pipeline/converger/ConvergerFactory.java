package gov.va.sparkcql.pipeline.converger;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.ComponentFactory;

public abstract class ConvergerFactory extends ComponentFactory {

    public ConvergerFactory(Configuration configuration) {
        super(configuration);
    }

    public abstract Converger create();
}
