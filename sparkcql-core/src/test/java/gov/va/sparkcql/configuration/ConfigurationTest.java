package gov.va.sparkcql.configuration;

import gov.va.sparkcql.pipeline.optimizer.DefaultOptimizer;
import gov.va.sparkcql.pipeline.optimizer.Optimizer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigurationTest {

    @Test
    public void should_support_reading_a_scalar_setting() {
        var cfg = new EnvironmentConfiguration();
        cfg.writeSetting("foo", "bar");
        assertEquals("bar", cfg.readSetting("foo").get());
    }

    @Test
    public void should_support_settings_with_defaults() {
        var cfg = new EnvironmentConfiguration();
        assertEquals("baz", cfg.readSetting("foo", "baz"));
    }

    @Test
    public void should_support_single_binding() {
        var cfg = new EnvironmentConfiguration();
        cfg.writeBinding(Optimizer.class, DefaultOptimizer.class);
        var bindings = cfg.readBinding(Optimizer.class);
        assertEquals(1, bindings.size());
    }

    @Test
    public void should_overwrite_single_binding() {
        var cfg = new EnvironmentConfiguration();
        cfg.writeBinding(Optimizer.class, Optimizer.class);
        cfg.writeBinding(Optimizer.class, DefaultOptimizer.class);
        var bindings = cfg.readBinding(Optimizer.class);
        assertEquals(1, bindings.size());
    }

    @Test
    public void should_allow_multiple_bindings() {
        var cfg = new EnvironmentConfiguration();
        var x = List.of(Optimizer.class, DefaultOptimizer.class);
        cfg.writeBinding(Optimizer.class, List.of(Optimizer.class, DefaultOptimizer.class));
        var bindings = cfg.readBinding(Optimizer.class);
        assertEquals(2, bindings.size());
    }

    @Test
    public void should_throw_meaningful_error_when_not_found() {
        var cfg = new EnvironmentConfiguration();
        try {
            var bindings = cfg.readBinding(Optimizer.class);
        } catch (Exception e) {
            assertEquals("Unable to locate binding for interface gov.va.sparkcql.pipeline.optimizer.Optimizer", e.getMessage());
        }
    }
}