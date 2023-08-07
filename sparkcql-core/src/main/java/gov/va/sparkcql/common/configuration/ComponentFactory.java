package gov.va.sparkcql.common.configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.spark.sql.SparkSession;

public final class ComponentFactory {

    public static <T> T createFromConfiguration(ConfigKey configurationKey) {
        return createFromConfiguration(configurationKey, Configuration.getGlobalConfig());
    }

    public static <T> T createFromConfiguration(ConfigKey configurationKey, Configuration cfg) {
        return internalCreateFromConfiguration(configurationKey.toString(), cfg);
    }

    public static <T> T createFromConfiguration(String configurationKey) {
        return createFromConfiguration(configurationKey, Configuration.getGlobalConfig());
    }

    public static <T> T createFromConfiguration(String configurationKey, Configuration cfg) {
        return internalCreateFromConfiguration(configurationKey, cfg);
    }

    @SuppressWarnings("unchecked")
    private static <T> T internalCreateFromConfiguration(String key, Configuration cfg) {
        try {
            Class<?> clazz;
            String className = cfg.read(key);
            if (className == null) {
                throw new RuntimeException("No class set for configuration '" + key + "'. Update environment variable or set in SparkCQLSession.");
            }
            clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();
            return (T) instance;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static SparkSession createSpark() {
        return createSpark(Configuration.getGlobalConfig());
    }

    public static SparkSession createSpark(Configuration cfg) {
        var spark = SparkSession.builder()
            .master(cfg.read(ConfigKey.SPARKCQL_SPARK_MASTER, "local[2]"))
            .config("spark.memory.offHeap.enabled", true)
            .config("spark.memory.offHeap.size", "16g")
            .getOrCreate();
        spark.sparkContext().setLogLevel(cfg.read(ConfigKey.SPARKCQL_SPARK_LOGLEVEL, "ERROR"));
        return spark;
    }    
}