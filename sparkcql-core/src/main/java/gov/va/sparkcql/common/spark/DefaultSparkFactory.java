package gov.va.sparkcql.common.spark;

import org.apache.spark.sql.SparkSession;

public class DefaultSparkFactory implements SparkFactory {

    @Override
    public SparkSession create() {
        var spark = SparkSession.builder().getOrCreate();
        return spark;
    }
}