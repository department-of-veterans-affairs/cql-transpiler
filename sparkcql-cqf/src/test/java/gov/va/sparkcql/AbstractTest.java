package gov.va.sparkcql;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.configuration.SparkFactory;

public class AbstractTest extends AbstractModule {
    @Override
    protected void configure() {
        bind(SparkFactory.class).to(LocalSparkFactory.class);
    }

    protected Injector getInjector() {
        return Guice.createInjector(this);
    }
}