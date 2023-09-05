package gov.va.sparkcql.runtime;

import gov.va.sparkcql.configuration.Configuration;
import org.apache.spark.sql.SparkSession;

public class DefaultSparkFactory implements SparkFactory {

    @Override
    public SparkSession create(Configuration configuration) {
        return SparkSession.builder().getOrCreate();
    }
}