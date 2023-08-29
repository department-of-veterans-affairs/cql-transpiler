package gov.va.sparkcql.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnvironmentConfiguration implements Configuration {

    private Map<String, String> runtimeConfig = new HashMap<String, String>();

    @Override
    public Optional<String> readSetting(String key) {
        return Optional.of(readSetting(key, null));
    }

    @Override
    public String readSetting(String key, String defaultValue) {
        if (runtimeConfig.containsKey(key)) {
            return runtimeConfig.get(key);
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
    public void writeSetting(String key, String value) {
        runtimeConfig.put(key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <I, T> Class<T> readBinding(Class<I> interfaceClass) {
        try {
            Class<?> cls = Class.forName(interfaceClass.getCanonicalName());
            return (Class<T>)cls;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <I, T> void writeBinding(Class<I> interfaceClass, Class<T> implementationClass) {
        this.writeSetting(interfaceClass.getCanonicalName(), implementationClass.getCanonicalName());
    }

    @Override
    public <I, T> void writeBinding(Class<I> interfaceClass, List<Class<T>> implementationClasses) {
        var canonicalNames = implementationClasses.stream()
                .map(Class::getCanonicalName);
        var concat = String.join(",", canonicalNames.collect(Collectors.toList()));
        this.writeSetting(interfaceClass.getCanonicalName(), concat);
    }
}