package gov.va.sparkcql.io;

import gov.va.sparkcql.configuration.ServiceModule;
import gov.va.sparkcql.runtime.LocalSparkFactory;
import gov.va.sparkcql.log.Log;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssetTest extends ServiceModule {

    @Test
    public void should_read_file_modality() {
        checkResults(new Asset("file://src/test/resources/mock-model/valueset").read());
    }

    @Test
    public void should_read_classpath_modality() {
        checkResults(new Asset("resource://mock-model/valueset").read());
    }

    @Test
    public void should_read_spark_modality() {
        // Can only run this test if winutils is configured to run spark storage locally.
        if (System.getenv("HADOOP_HOME") != null) {
            // In Spark mode, Asset will use any established spark connection.
            var spark = new LocalSparkFactory(getConfiguration()).create();
            checkResults(new Asset("spark://mock-model/valueset").read());
        } else {
            Log.warn("Winutils, HADOOP_HOME, & hadoop.home.dir were not configured. Skipping Spark mode Asset test.");
        }
    }

    private void checkResults(List<String> results) {
        assertFalse(results.isEmpty());
        assertTrue(results.get(0).contains("identifier"));
    }
}