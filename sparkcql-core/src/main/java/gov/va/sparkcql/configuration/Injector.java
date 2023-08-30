package gov.va.sparkcql.configuration;

import gov.va.sparkcql.log.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class Injector {

    private Configuration configuration;

    public Injector(Configuration configuration) {
        this.configuration = configuration;
    }

    public <I> I getInstance(Class<I> interfaceClass) {
        var instances = internalGetInstances(interfaceClass, interfaceClass, false);
        if (instances.size() > 1)
            Log.warn("Injector found multiple implementations for interface " + interfaceClass.getCanonicalName());
        return instances.get(0);
    }

    public <I> I getInstance(Class<I> interfaceClass, Class<? extends I> defaultImplementationClass) {
        var instances = internalGetInstances(interfaceClass, interfaceClass, true);
        if (instances.size() > 1)
            Log.warn("Injector found multiple implementations for interface " + interfaceClass.getCanonicalName());
        return instances.get(0);
    }

    public <I> List<? extends I> getInstances(Class<I> interfaceClass) {
        return internalGetInstances(interfaceClass, interfaceClass, false);
    }

    public <I> List<? extends I> getInstances(Class<I> interfaceClass, Class<? extends I> defaultImplementationClass) {
        return internalGetInstances(interfaceClass, defaultImplementationClass, true);
    }

    @SuppressWarnings("unchecked")
    public <I> List<? extends I> internalGetInstances(Class<I> interfaceClass, Class<? extends I> defaultImplementationClass, boolean hasDefault) {
        // In the special case where the interface being requested is a Configuration
        // then return the configuration we already have. This is important b/c it's this
        // configuration which contains vital binding definitions configured earlier in the stack.
        if (interfaceClass == Configuration.class || interfaceClass == EnvironmentConfiguration.class)
            return List.of((I)this.configuration);

        List<Class<I>> bindings;
        if (hasDefault)
            bindings = configuration.readBinding(interfaceClass, defaultImplementationClass);
        else
            bindings = configuration.readBinding(interfaceClass);

        return bindings.stream().map(binding -> {
            try {
                var constructors = binding.getConstructors();
                var modifiers = binding.getModifiers();

                // Validation
                if (Modifier.isAbstract(modifiers))
                    throw new RuntimeException(interfaceClass.getCanonicalName() + " is abstract and cannot be instantiated.");

                if (Modifier.isInterface(modifiers))
                    throw new RuntimeException(interfaceClass.getCanonicalName() + " is an interface and cannot be instantiated.");

                if (constructors.length > 1)
                    throw new RuntimeException("Injection only supports single constructors but multiple were found for " + interfaceClass.getCanonicalName() + ".");

                // For every defined parameter type, try to lookup a binding. If one doesn't exist,
                // throw an error. Since we know the parameter types, we can use another injector
                // to resolve the implementations.
                var parameterInjector = new Injector(configuration);
                var params = Arrays.stream(constructors[0].getParameterTypes())
                        .map(parameterInjector::getInstance).toArray(Object[]::new);

                // Dynamically construct and return.
                return (I)constructors[0].newInstance(params);

            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }
}