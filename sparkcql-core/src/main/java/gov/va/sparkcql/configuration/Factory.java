package gov.va.sparkcql.configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Factory {

    private Configuration configuration;

    public Factory(Configuration configuration) {
        this.configuration = configuration;
    }

    protected Configuration getConfiguration() {
        return this.configuration;
    }
}