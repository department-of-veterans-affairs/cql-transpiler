package gov.va.sparkcql;

import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.Test;

public class SparkAntlrConflictTest {

    @Test
    public void should_not_break_spark() {
        // Confirms the proper Spark ANTLR version is loaded instead of CQFs higher version.
        var spark = SparkSession.builder()
                .master("local[1]")
                .getOrCreate();
        spark.sql("select 12345 foo");
    }
}