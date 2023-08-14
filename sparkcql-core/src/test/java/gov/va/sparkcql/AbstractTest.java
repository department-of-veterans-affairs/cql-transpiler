package gov.va.sparkcql;

import com.google.inject.Guice;
import com.google.inject.Injector;

import gov.va.sparkcql.common.configuration.AbstractTestModule;
import gov.va.sparkcql.common.spark.LocalSparkFactory;
import gov.va.sparkcql.common.spark.SparkFactory;

public class AbstractTest extends AbstractTestModule {
    @Override
    protected void configure() {
        bind(SparkFactory.class).to(LocalSparkFactory.class);
    }

    protected Injector getInjector() {
        return Guice.createInjector(this);
    }
}