package gov.va.sparkcql.configuration;

import gov.va.sparkcql.configuration.Configuration;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ComponentFactory implements Serializable {

    private Configuration configuration;

    public ComponentFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    protected Configuration getConfiguration() {
        return this.configuration;
    }
}