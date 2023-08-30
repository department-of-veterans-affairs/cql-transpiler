package gov.va.sparkcql;

import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.configuration.SparkFactory;
import org.apache.spark.sql.SparkSession;

public class AbstractTest {

    SparkSession getSpark() {
        return new LocalSparkFactory().create();
    }
}