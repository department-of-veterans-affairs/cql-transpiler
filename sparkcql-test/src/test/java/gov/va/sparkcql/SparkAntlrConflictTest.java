package gov.va.sparkcql;

import gov.va.sparkcql.runtime.SparkFactory;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.runtime.LocalSparkFactory;

public class SparkAntlrConflictTest extends AbstractTest {

    private final SparkFactory sparkFactory = new LocalSparkFactory();

    @Test
    public void should_not_break_spark() {
        // Confirms the proper Spark ANTLR version is loaded instead of CQFs higher version.
        spark.sql("select 12345 foo");
    }
}