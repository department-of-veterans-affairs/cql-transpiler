package gov.va.sparkcql.pipeline.converger;

import gov.va.sparkcql.configuration.Configuration;

public class DefaultConvergerFactory implements ConvergerFactory {

    @Override
    public Converger create(Configuration configuration) {
        return new DefaultConverger();
    }
}