package gov.va.sparkcql.common.di;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ServiceLoader;

import gov.va.sparkcql.common.configuration.Configuration;

public final class ServiceContext {

    public static <T> T createOne(Class<T> clazz) {
        return createOne(clazz, Configuration.getGlobalConfig());
    }

    public static <T> T createOne(Class<T> clazz, Configuration cfg) {
        try {
            var implementationClass = cfg.read(clazz.getCanonicalName(), null);
            if (implementationClass != null) {
                return instantiate(implementationClass, clazz);
            } else {
                var providers = ServiceLoader.load(clazz);
                return providers.findFirst().get();
            }
        } catch (NoSuchElementException ex) {
            throw new RuntimeException("ServiceContext couldn't find implementation for " + clazz.getCanonicalName());
        } catch (Exception ex) {
            throw new RuntimeException("ServiceContext couldn't create implementation found for " + clazz.getCanonicalName());
        }
    }

    public static <T> List<T> createMany(Class<T> clazz) {
        return createMany(clazz, Configuration.getGlobalConfig());
    }

    public static <T> List<T> createMany(Class<T> clazz, Configuration cfg) {
        try {
            var implementationClasses = cfg.read(clazz.getCanonicalName(), null);
            if (implementationClasses != null) {
                return Arrays.stream(implementationClasses.split(";"))
                    .map(f-> instantiate(f, clazz))
                    .toList();
            } else {
                var providers = ServiceLoader.load(clazz);
                return providers.stream().map(f -> f.get()).toList();
            }
        } catch (NoSuchElementException ex) {
            throw new RuntimeException("ServiceContext couldn't find implementation found for " + clazz.getCanonicalName());
        } catch (Exception ex) {
            throw new RuntimeException("ServiceContext couldn't create implementation found for " + clazz.getCanonicalName());
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