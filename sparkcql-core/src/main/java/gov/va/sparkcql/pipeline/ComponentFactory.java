package gov.va.sparkcql.pipeline;

import gov.va.sparkcql.configuration.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ComponentFactory {

    private Configuration configuration;

    public ComponentFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    protected Configuration getConfiguration() {
        return this.configuration;
    }
}