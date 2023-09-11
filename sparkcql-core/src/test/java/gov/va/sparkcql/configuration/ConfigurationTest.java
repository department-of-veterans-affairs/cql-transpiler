package gov.va.sparkcql.configuration;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigurationTest {

    @Test
    public void should_support_reading_a_scalar_setting() {
        var cfg = new EnvironmentConfiguration()
            .writeSetting("foo", "bar");
        assertEquals("bar", cfg.readSetting("foo").get());
    }

    @Test
    public void should_support_settings_with_defaults() {
        var cfg = new EnvironmentConfiguration();
        assertEquals("baz", cfg.readSetting("foo", "baz"));
    }

    @Test
    public void should_support_single_binding() {
        var cfg = new EnvironmentConfiguration()
            .writeBinding(String.class, String.class);
        var bindings = cfg.readBinding(String.class);
        assertEquals(1, bindings.size());
    }

    @Test
    public void should_overwrite_single_binding() {
        var cfg = new EnvironmentConfiguration()
            .writeBinding(String.class, String.class)
            .writeBinding(String.class, String.class);
        var bindings = cfg.readBinding(String.class);
        assertEquals(1, bindings.size());
    }

    @Test
    public void should_allow_multiple_bindings() {
        var cfg = new EnvironmentConfiguration();
        var x = List.of(String.class, String.class);
        cfg.writeBinding(String.class, List.of(String.class, String.class));
        var bindings = cfg.readBinding(String.class);
        assertEquals(2, bindings.size());
    }

    @Test
    public void should_throw_meaningful_error_when_not_found() {
        var cfg = new EnvironmentConfiguration();
        try {
            var bindings = cfg.readBinding(String.class);
        } catch (Exception e) {
            assertEquals("Unable to locate binding for interface gov.va.sparkcql.pipeline.optimizer.Optimizer", e.getMessage());
        }
    }
}