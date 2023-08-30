package gov.va.sparkcql;

import gov.va.sparkcql.configuration.*;
import org.apache.spark.sql.SparkSession;

public class AbstractTest {

    SparkSession getSpark() {
        return new LocalSparkFactory().create();
    }
}