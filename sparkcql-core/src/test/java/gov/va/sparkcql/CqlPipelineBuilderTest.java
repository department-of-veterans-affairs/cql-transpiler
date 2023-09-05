package gov.va.sparkcql;

import gov.va.sparkcql.fixture.mock.MockCompilerFactory;
import gov.va.sparkcql.fixture.mock.MockEvaluatorFactory;
import gov.va.sparkcql.fixture.mock.MockModelAdapterFactory;
import gov.va.sparkcql.pipeline.compiler.CompilerFactory;
import gov.va.sparkcql.pipeline.converger.ConvergerFactory;
import gov.va.sparkcql.pipeline.evaluator.EvaluatorFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterFactory;
import gov.va.sparkcql.pipeline.optimizer.OptimizerFactory;
import gov.va.sparkcql.pipeline.retriever.RetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;
import gov.va.sparkcql.runtime.LocalSparkFactory;
import gov.va.sparkcql.runtime.SparkFactory;
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
}