package gov.va.sparkcql.configuration;

import gov.va.sparkcql.pipeline.Pipeline;
import gov.va.sparkcql.pipeline.optimizer.Optimizer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InjectorTest {

    @Test
    public void should_fail_gracefully_when_instantiating_interfaces() {
        try {
            var injector = new Injector(new EnvironmentConfiguration());
            injector.getInstance(Optimizer.class);
        } catch (Exception e) {
            assertEquals("Unable to locate binding for interface gov.va.sparkcql.pipeline.optimizer.Optimizer", e.getMessage());
        }
    }

        @Test
    public void should_inject_multiple_constructor_params() {
        // TODO: Add basic support for constructor params
        // var injector = new Injector(new EnvironmentConfiguration());
        //        var constructors = Pipeline.class.getConstructors();
        //        var x = constructors;

    }
}
