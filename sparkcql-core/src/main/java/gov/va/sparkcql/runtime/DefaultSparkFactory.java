package gov.va.sparkcql.runtime;

import gov.va.sparkcql.configuration.Configuration;
import org.apache.spark.sql.SparkSession;

public class DefaultSparkFactory extends SparkFactory {

    public DefaultSparkFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public SparkSession create() {
        var spark = SparkSession.builder().getOrCreate();
        return spark;
    }
}