package gov.va.sparkcql;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategyFactory;
import gov.va.sparkcql.runtime.LocalSparkFactory;
import gov.va.sparkcql.runtime.SparkFactory;
import org.apache.spark.sql.SparkSession;

public abstract class AbstractTest {

    protected final Configuration configuration = new EnvironmentConfiguration()
            .writeBinding(SparkFactory.class, LocalSparkFactory.class)
            // Database support unavailable when running locally so drop the qualifier.
            .writeSetting(TemplateResolutionStrategyFactory.TEMPLATE_RESOLUTION_STRATEGY, "${domain}");

    protected final Injector injector = new Injector(configuration);
    protected final SparkFactory sparkFactory = injector.getInstance(SparkFactory.class);
    protected final SparkSession spark = sparkFactory.create(configuration);
}