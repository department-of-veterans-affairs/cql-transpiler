package gov.va.sparkcql;

import gov.va.sparkcql.configuration.ServiceModule;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.runtime.LocalSparkFactory;

public class SparkAntlrConflictTest extends ServiceModule {

    @Test
    public void should_not_break_spark() {
        // Confirms the proper Spark ANTLR version is loaded instead of CQF's higher version.
        getSpark().sql("select 12345 foo");
    }
}