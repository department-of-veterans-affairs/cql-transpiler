package gov.va.sparkcql.common.spark;

import org.apache.spark.sql.SparkSession;

public interface SparkFactory {

    SparkSession create();
}