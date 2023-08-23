package gov.va.sparkcql.configuration;

import com.google.inject.multibindings.OptionalBinder;

public abstract class AbstractTestModule extends com.google.inject.AbstractModule {
    @Override
    protected void configure() {
        OptionalBinder.newOptionalBinder(binder(), SparkFactory.class).setDefault().to(LocalSparkFactory.class);
    }
}