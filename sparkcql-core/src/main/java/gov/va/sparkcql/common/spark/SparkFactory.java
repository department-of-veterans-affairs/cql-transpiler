package gov.va.sparkcql.common.spark;

import org.apache.spark.sql.SparkSession;

import gov.va.sparkcql.common.di.NullaryFactory;

public interface SparkFactory extends NullaryFactory<SparkSession> {
}