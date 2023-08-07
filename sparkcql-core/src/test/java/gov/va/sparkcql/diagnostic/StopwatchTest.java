package gov.va.sparkcql.diagnostic;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.common.diagnostic.Stopwatch;

public class StopwatchTest {

    @Test
    public void should_calculate_duration() {
        var watch = new Stopwatch();
        watch.sleep(10);
        watch.stop();
        assertTrue(watch.getLapse().toMillis() >= 10);
    }

    @Test
    public void should_format_properly() {
        var watch = new Stopwatch();
        watch.sleep(100);
        watch.stop();
        assertTrue(watch.toString().startsWith("0h:0m:0s:") && watch.toString().endsWith("ms"));
    }
}