package gov.va.sparkcql;

import org.apache.spark.sql.SparkSession;

public class Context {
    private SparkSession spark;
    
    private SparkSession spark() {
        return this.spark;
    }
}
