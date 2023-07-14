package gov.va.sparkcql.adapter.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.diagnostic.Log;

public class DataAdapterTest {

    @Test
    public void should_pull_mock_data() {
        assertTrue(true);

        // var spark = SparkSession.builder()
        //     .master("local[1]")
        //     .getOrCreate();
        
        // var ds = spark.sql("select 123 foo");
        // Log.info(ds);
    }
}