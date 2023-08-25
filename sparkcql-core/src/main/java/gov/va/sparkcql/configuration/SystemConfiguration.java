package gov.va.sparkcql.configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SystemConfiguration implements Serializable {

    private String CQL_SOURCE_FILE_REPOSITORY_PATH = "sparkcql.cqlsourcefilerepository.path";
    private String CQL_SOURCE_FILE_REPOSITORY_EXT = "sparkcql.cqlsourcefilerepository.extension";
    private String RESOLUTION_STRATEGY_TEMPLATE = "sparkcql.resolutionstrategy.formula";

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

    public String getCqlSourceFileRepositoryPath() {
        return read(CQL_SOURCE_FILE_REPOSITORY_PATH, "./");
    }

    public void setCqlSourceFileRepositoryPath(String path) {
        write(CQL_SOURCE_FILE_REPOSITORY_PATH, path);
    }

    public SystemConfiguration withCqlSourceFileRepositoryPath(String path) {
        write(CQL_SOURCE_FILE_REPOSITORY_PATH, path);
        return this;
    }

    public String getCqlSourceFileRepositoryExt() {
        return read(CQL_SOURCE_FILE_REPOSITORY_EXT, "cql");
    }

    public void setCqlSourceFileRepositoryExt(String extension) {
        write(CQL_SOURCE_FILE_REPOSITORY_EXT, extension);
    }

    public SystemConfiguration withCqlSourceFileRepositoryExt(String extension) {
        write(CQL_SOURCE_FILE_REPOSITORY_EXT, extension);
        return this;
    }

    public String getResolutionStrategyTemplate() {
        return read(RESOLUTION_STRATEGY_TEMPLATE, "${model}.${domain}");
    }

    public void setResolutionStrategyFormula(String formula) {
        write(RESOLUTION_STRATEGY_TEMPLATE, formula);
    }

    public SystemConfiguration withResolutionStrategyFormula(String formula) {
        write(RESOLUTION_STRATEGY_TEMPLATE, formula);
        return this;
    }
}