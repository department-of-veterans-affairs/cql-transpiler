package gov.va.sparkcql.common.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.OptionalBinder;

import gov.va.sparkcql.common.spark.DefaultSparkFactory;
import gov.va.sparkcql.common.spark.SparkFactory;

public class DefaultModule extends AbstractModule {
    @Override
    protected void configure() {
        OptionalBinder.newOptionalBinder(binder(), SparkFactory.class).setDefault().to(DefaultSparkFactory.class);
    }
}