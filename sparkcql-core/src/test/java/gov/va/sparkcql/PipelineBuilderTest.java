/*
package gov.va.sparkcql;

import org.junit.jupiter.api.Test;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PipelineBuilderTest {
    @Test
    public void should_auto_load_services() {
        var pipelineBuilder = new CqlPipelineBuilder();
        var plan = new CqlPipelineBuilder()
                .withBinding(SparkFactory.class, LocalSparkFactory.class)
                .withBinding(ModelAdapterFactory.class, MockModelAdapterFactory.class)
                .withBinding(CompilerFactory.class, MockCompilerFactory.class)
                .withBinding(EvaluatorFactory.class, MockEvaluatorFactory.class)
                .plan(new QualifiedIdentifier().withId("MOCK_LIBRARY"))
                .run();
        assertEquals(1, plan.getLibraries().size());
    }
    @Test
    public void should_provide_fluent_api_for_evaluation() {

        var r = new CqlPipelineBuilder()
                .withSetting("", "")
                .withCql("MyLibrary")
                .withParameter("StartDate", new Date())
                .withPlan(null)
                .evaluate(new QualifiedIdentifier().withId("MyLibrary"))
                .byContext()
                .run();
    }

    @Test
    public void should_provide_fluent_api_for_required_data() {

        var r = new CqlPipelineBuilder()
                .withSetting("", "")
                .withCql("MyLibrary")
                .retrieve(new QualifiedIdentifier().withId("MyLibrary"))
                .byDataType()
                .run();
    }

    @Test
    public void should_provide_fluent_api_for_planning() {

        var r = new CqlPipelineBuilder()
                .withSetting("", "")
                .withCql("MyLibrary")
                .plan(new QualifiedIdentifier().withId("MyLibrary"))
                .run();

    }
}
*/

