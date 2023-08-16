package gov.va.sparkcql.configuration;

import java.util.ServiceLoader;

import com.google.inject.AbstractModule;
import com.google.inject.Module;

// https://stackoverflow.com/questions/902639/has-anyone-used-serviceloader-together-with-guice
public class ModuleLoader<M extends Module> extends AbstractModule {

    private final Class<M> type;

    public ModuleLoader(Class<M> type) {
        this.type = type;
    }

    public static <M extends Module> ModuleLoader<M> of(Class<M> type) {
        return new ModuleLoader<M>(type);
    }

    @Override
    protected void configure() {
        ServiceLoader<M> modules = ServiceLoader.load(type);
        for (Module module : modules) {
            install(module);
        }
    }
}