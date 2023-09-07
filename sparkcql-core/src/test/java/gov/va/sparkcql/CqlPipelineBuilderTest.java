package gov.va.sparkcql;

import gov.va.sparkcql.pipeline.converger.ConvergerFactory;
import gov.va.sparkcql.pipeline.optimizer.OptimizerFactory;
import gov.va.sparkcql.pipeline.retriever.RetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;
import gov.va.sparkcql.pipeline.runtime.SparkFactory;
import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CqlPipelineBuilderTest {

    @Test
    public void should_register_loadable_services() {
        assertEquals(1, ServiceLoader.load(ConvergerFactory.class).stream().count());
        assertEquals(1, ServiceLoader.load(OptimizerFactory.class).stream().count());
        assertEquals(1, ServiceLoader.load(TableResolutionStrategyFactory.class).stream().count());
        assertEquals(1, ServiceLoader.load(RetrieverFactory.class).stream().count());
        assertEquals(1, ServiceLoader.load(SparkFactory.class).stream().count());
    }

//    @Test
//    public void should_auto_load_services() {
//        var pipelineBuilder = new CqlPipelineBuilder();
//        var plan = new CqlPipelineBuilder()
//                .withBinding(SparkFactory.class, LocalSparkFactory.class)
//                .withBinding(ModelAdapterFactory.class, MockModelAdapterFactory.class)
//                .withBinding(CompilerFactory.class, MockCompilerFactory.class)
//                .withBinding(EvaluatorFactory.class, MockEvaluatorFactory.class)
//                .plan(new QualifiedIdentifier().withId("MOCK_LIBRARY"))
//                .run();
//        assertEquals(1, plan.getLibraries().size());
//    }

//    @Test
//    public void should_provide_fluent_api_for_evaluation() {
//
//        var r = new CqlPipelineBuilder()
//                .withSetting("", "")
//                .withCql("MyLibrary")
//                .withParameter("StartDate", new Date())
//                .withPlan(null)
//                .evaluate(new QualifiedIdentifier().withId("MyLibrary"))
//                .byContext()
//                .run();
//    }
//
//    @Test
//    public void should_provide_fluent_api_for_required_data() {
//
//        var r = new CqlPipelineBuilder()
//                .withSetting("", "")
//                .withCql("MyLibrary")
//                .retrieve(new QualifiedIdentifier().withId("MyLibrary"))
//                .byDataType()
//                .run();
//    }
//
//    @Test
//    public void should_provide_fluent_api_for_planning() {
//
//        var r = new CqlPipelineBuilder()
//                .withSetting("", "")
//                .withCql("MyLibrary")
//                .plan(new QualifiedIdentifier().withId("MyLibrary"))
//                .run();
//
//    }
}