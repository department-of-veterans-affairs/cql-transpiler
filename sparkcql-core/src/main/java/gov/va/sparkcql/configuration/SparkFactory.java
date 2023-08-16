package gov.va.sparkcql.configuration;

import org.apache.spark.sql.SparkSession;

public interface SparkFactory {

    SparkSession create();
}