package gov.va.sparkcql.common.configuration;

import java.util.HashMap;
import java.util.Map;

public final class Configuration {

    private static Configuration globalConfig = new Configuration();
    private Map<String, String> runtimeConfig = new HashMap<String, String>();
    
    public static void setGlobalConfig(Configuration globalConfiguration) {
        Configuration.globalConfig = globalConfiguration;
    }

    public static Configuration getGlobalConfig() {
        return Configuration.globalConfig;
    }

    private String enumToKey(ConfigKey key) {
        return key.toString().replace('_', '.').toLowerCase();
    }

    public String read(ConfigKey key) {
        return read(key, null);
    }

    public String read(ConfigKey key, String defaultValue) {
        return read(enumToKey(key), defaultValue);
    }

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

    public void write(String key, String value) {
        runtimeConfig.put(key, value);
    }

    public void write(ConfigKey key, String value) {
        runtimeConfig.put(enumToKey(key), value);
    }
}