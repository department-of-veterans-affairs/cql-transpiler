package gov.va.sparkcql.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.OptionalBinder;

public class DefaultModule extends AbstractModule {
    @Override
    protected void configure() {
        OptionalBinder.newOptionalBinder(binder(), SparkFactory.class).setDefault().to(DefaultSparkFactory.class);
    }
}