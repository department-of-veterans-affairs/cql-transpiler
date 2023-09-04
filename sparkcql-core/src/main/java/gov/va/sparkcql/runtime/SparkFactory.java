package gov.va.sparkcql.runtime;

import gov.va.sparkcql.pipeline.ComponentFactory;
import gov.va.sparkcql.configuration.Configuration;
import org.apache.spark.sql.SparkSession;

public abstract class SparkFactory extends ComponentFactory {

    public SparkFactory(Configuration configuration) {
        super(configuration);
    }

    public abstract SparkSession create();
}