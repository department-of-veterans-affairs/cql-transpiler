package gov.va.sparkcql;

import java.util.HashMap;
import java.util.Map;

public final class Configuration {

    private Map<String, String> localConfiguration = new HashMap<String, String>();
    private Map<String, String> sessionConfiguration;

    public Configuration() {
        this.sessionConfiguration = Map.of();
    }

    public Configuration(Map<String, String> sessionConfiguration) {
        this.sessionConfiguration = sessionConfiguration;
    }

    public String read(String key) {
        return read(key, null);
    }

    public String read(String key, String defaultValue) {
        if (localConfiguration.containsKey(key)) {
            return localConfiguration.get(key);
        } else if (sessionConfiguration.containsKey(key)) {
            return sessionConfiguration.get(key);
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

    public void write(String key, String value) {
        localConfiguration.put(key, value);
    }

    public void write(Configuration componentConfiguration) {
        this.localConfiguration = componentConfiguration.localConfiguration;
    }

    public Configuration with(String key, String value) {
        write(key, value);
        return this;
    }
}
