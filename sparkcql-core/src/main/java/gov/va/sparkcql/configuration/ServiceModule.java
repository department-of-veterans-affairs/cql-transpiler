package gov.va.sparkcql.configuration;

import gov.va.sparkcql.runtime.LocalSparkFactory;
import gov.va.sparkcql.runtime.SparkFactory;
import org.apache.spark.sql.SparkSession;

public abstract class ServiceModule {

    private final Configuration configuration;
    private final SparkFactory sparkFactory;
    private final SparkSession spark;

    public ServiceModule() {
        configuration = configure();
        sparkFactory = new LocalSparkFactory();
        spark = sparkFactory.create(configuration);
    }

    protected Configuration configure() {
        return new EnvironmentConfiguration();
    }

    protected SparkSession getSpark() {
        return spark;
    }

    protected SparkFactory getSparkFactory() {
        return sparkFactory;
    }

    protected Configuration getConfiguration() {
        return configuration;
    }
}