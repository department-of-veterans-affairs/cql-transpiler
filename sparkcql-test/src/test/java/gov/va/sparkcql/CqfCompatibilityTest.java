package gov.va.sparkcql;

import org.junit.jupiter.api.Test;

import gov.va.sparkcql.configuration.LocalSparkFactory;

public class CqfCompatibilityTest {

    @Test
    public void should_not_break_spark() {
        // Confirms the proper Spark ANTLR version is loaded instead of CQF's higher version.
        var spark = new LocalSparkFactory().create();
        spark.sql("select 12345 foo");
    }
}