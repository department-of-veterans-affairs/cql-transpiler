package gov.va.sparkcql.configuration;

import java.util.*;
import java.util.stream.Collectors;

public class EnvironmentConfiguration implements Configuration {

    // Defines any setting set during runtime which excludes Environment variables or System Properties.
    private final Map<String, String> runtimeSettings = new HashMap<String, String>();

    @Override
    public Optional<String> readSetting(String key) {
        return Optional.ofNullable(readSetting(key, null));
    }

    @Override
    public String readSetting(String key, String defaultValue) {
        if (runtimeSettings.containsKey(key)) {
            return runtimeSettings.get(key);
        } else if (System.getProperty(key) != null) {
            return System.getProperty(key);
        } else {
            var envVarFriendlyName = key.replace('.', '_').toUpperCase();
            if (System.getenv(envVarFriendlyName) != null) {
                return System.getenv(envVarFriendlyName);
            } else {
                return defaultValue;
            }
        }
    }

    @Override
    public Map<String, String> readAllSettings() {
        var allSettings = new HashMap<String, String>();
        allSettings.putAll(System.getenv());
        allSettings.putAll(runtimeSettings);
        return allSettings;
    }

    @Override
    public void writeSetting(String key, String value) {
        runtimeSettings.put(key, value);
    }

    @Override
    public <I> List<Class<I>> readBinding(Class<I> interfaceClass) {
        return internalReadBinding(interfaceClass, interfaceClass, false);
    }

    @Override
    public <I> List<Class<I>> readBinding(Class<I> interfaceClass, Class<? extends I> defaultImplementationClass) {
        return internalReadBinding(interfaceClass, defaultImplementationClass, true);
    }

    @SuppressWarnings("unchecked")
    public <I> List<Class<I>> internalReadBinding(Class<I> interfaceClass, Class<? extends I> defaultImplementationClass, boolean hasDefault) {
        // In the special case where the interface being requested is a Configuration
        // (aka ourselves), then return ourselves.
        if (interfaceClass == Configuration.class || interfaceClass == EnvironmentConfiguration.class)
            return List.of((Class<I>)this.getClass());

        // Otherwise, get it from settings.
        String bindingSetting = "";
        if (hasDefault) {
            bindingSetting = readSetting(
                    interfaceClass.getCanonicalName(),
                    defaultImplementationClass.getCanonicalName());
        } else {
            bindingSetting = readSetting(
                    interfaceClass.getCanonicalName())
                    .orElseThrow(() -> new RuntimeException("Unable to locate binding for interface " + interfaceClass.getCanonicalName()));
        }

        // Since multi-bindings are defined as comma delimited lists, split and
        // process assuming that's the case.
        return Arrays.stream(bindingSetting.split(","))
                .map(b -> {
                    try {
                        // For the classname string, produce a Class type.
                        return (Class<I>)Class.forName(b);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }).collect(Collectors.toList());
    }

    @Override
    public <I> void writeBinding(Class<I> interfaceClass, Class<? extends I> implementationClass) {
        this.writeSetting(interfaceClass.getCanonicalName(), implementationClass.getCanonicalName());
    }

    @Override
    public <I> void writeBinding(Class<I> interfaceClass, List<Class<? extends I>> implementationClasses) {
        var canonicalNames = implementationClasses.stream()
                .map(Class::getCanonicalName);
        var concat = String.join(",", canonicalNames.collect(Collectors.toList()));
        this.writeSetting(interfaceClass.getCanonicalName(), concat);
    }
}