package gov.va.sparkcql;

import gov.va.sparkcql.pipeline.converger.Converger;
import gov.va.sparkcql.pipeline.converger.ConvergerFactory;
import gov.va.sparkcql.pipeline.optimizer.Optimizer;
import gov.va.sparkcql.pipeline.optimizer.OptimizerFactory;
import gov.va.sparkcql.types.QualifiedIdentifier;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CqlPipelineBuilderTest {

    @Test
    public void should_register_loadable_services() {
        assertEquals(1, ServiceLoader.load(ConvergerFactory.class).stream().count());
        assertEquals(1, ServiceLoader.load(OptimizerFactory.class).stream().count());
    }

//    @Test
//    public void should_auto_load_services() {
//        var pipelineBuilder = new CqlPipelineBuilder();
//        var r = new CqlPipelineBuilder()
//                .plan(new QualifiedIdentifier().withId("MOCK_LIBRARY"));
//    }
}