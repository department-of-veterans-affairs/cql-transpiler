package gov.va.sparkcql.configuration;

import gov.va.sparkcql.log.Log;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

public final class Injector {

    private Configuration configuration;

    public Injector(Configuration configuration) {
        this.configuration = configuration;
    }

    public <I> I getInstance(Class<I> interfaceClass) {
        return getInstance(interfaceClass, interfaceClass);
    }

    public <I, T extends I> T getInstance(Class<I> interfaceClass, Class<T> defaultImplementationClass) {
        var instances = getInstances(interfaceClass, defaultImplementationClass);
        if (instances.size() > 1)
            Log.warn("Injector found multiple implementations for interface " + interfaceClass.getCanonicalName());
        return instances.get(0);
    }

    public <I> List<I> getInstances(Class<I> interfaceClass) {
        return getInstances(interfaceClass, interfaceClass);
    }

    @SuppressWarnings("unchecked")
    public <I, T extends I> List<T> getInstances(Class<I> interfaceClass, Class<T> defaultImplementationClass) {
        var bindings = configuration.readBinding(interfaceClass, defaultImplementationClass);
        return bindings.stream().map(binding -> {
            try {
                if (binding.getConstructors().length == 0)
                    throw new RuntimeException(interfaceClass.getCanonicalName() + " is not instantiable.");

                if (binding.getConstructors().length > 1)
                    throw new RuntimeException("Injection only supports single constructors but multiple were found for " + interfaceClass.getCanonicalName() + ".");

                return (T)binding.getConstructors()[0].newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}