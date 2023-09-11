package gov.va.sparkcql.pipeline.runtime;

import gov.va.sparkcql.pipeline.ComponentFactory;
import gov.va.sparkcql.configuration.Configuration;
import org.apache.spark.sql.SparkSession;

public interface SparkFactory extends ComponentFactory {

    public abstract SparkSession create(Configuration configuration);
}