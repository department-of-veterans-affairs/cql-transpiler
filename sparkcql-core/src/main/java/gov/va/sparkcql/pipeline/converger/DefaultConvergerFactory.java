package gov.va.sparkcql.pipeline.converger;

import gov.va.sparkcql.configuration.Configuration;

public class DefaultConvergerFactory extends ConvergerFactory {

    public DefaultConvergerFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Converger create() {
        return new DefaultConverger();
    }
}