package gov.va.sparkcql.configuration;

import java.util.HashMap;
import java.util.Map;

public final class SystemConfiguration {

    private Map<String, String> runtimeConfig = new HashMap<String, String>();
    
    public String read(String key) {
        return read(key, null);
    }

    public String read(String key, String defaultValue) {
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

    public <I, T> void writeBinding(Class<I> interfaceClass, Class<T> implementationClass) {
        this.write(interfaceClass.getCanonicalName(), implementationClass.getCanonicalName());
    }

    public void write(String key, String value) {
        runtimeConfig.put(key, value);
    }
}