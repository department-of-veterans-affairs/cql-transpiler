package gov.va.sparkcql;

import org.apache.spark.sql.SparkSession;

public abstract class TestBase {
    
    protected static final String OUTPUT_FOLDER = "./.temp/";

    protected SparkSession getSpark() {
        var spark = SparkSession.builder()
            .master("local[2]")
            .config("spark.memory.offHeap.enabled", true)
            .config("spark.memory.offHeap.size", "16g")              
            .getOrCreate();
        spark.sparkContext().setLogLevel("ERROR");

        return spark;
    }
}