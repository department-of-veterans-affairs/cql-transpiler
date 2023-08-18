package gov.va.sparkcql.configuration;

import java.io.Serializable;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.OptionalBinder;

public class DefaultModule extends AbstractModule implements Serializable {
    @Override
    protected void configure() {
        OptionalBinder.newOptionalBinder(binder(), SparkFactory.class).setDefault().to(DefaultSparkFactory.class);
    }
}