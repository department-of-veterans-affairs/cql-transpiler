package gov.va.sparkcql.common.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;

import gov.va.sparkcql.common.configuration.Configuration;

public final class Components {

    public static <T> T createOne(Class<T> clazz) {
        return createOne(clazz, Configuration.getGlobalConfig());
    }

    public static <T> T createOne(Class<T> clazz, Configuration cfg) {
        var implementationClass = cfg.read(clazz.getCanonicalName(), null);
        if (implementationClass != null) {
            return instantiate(implementationClass, clazz);
        } else {
            var providers = ServiceLoader.load(clazz);
            return providers.findFirst().get();
        }        
    }

    public static <T> List<T> createMany(Class<T> clazz) {
        return createMany(clazz, Configuration.getGlobalConfig());
    }

    public static <T> List<T> createMany(Class<T> clazz, Configuration cfg) {
        var implementationClasses = cfg.read(clazz.getCanonicalName(), null);
        if (implementationClasses != null) {
            return Arrays.stream(implementationClasses.split(";"))
                .map(f-> instantiate(f, clazz))
                .toList();
        } else {
            var providers = ServiceLoader.load(clazz);
            return providers.stream().map(f -> f.get()).toList();
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T instantiate(String className, Class<T> interfaceClazz) {
        try {
            Class<?> clazz;
            clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();
            return (T)instance;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}