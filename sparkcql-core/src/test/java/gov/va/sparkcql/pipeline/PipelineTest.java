package gov.va.sparkcql.pipeline;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.fixture.mock.*;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.converger.ConvergerFactory;
import gov.va.sparkcql.pipeline.converger.DefaultConvergerFactory;
import gov.va.sparkcql.pipeline.evaluator.EvaluatorFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterFactory;
import gov.va.sparkcql.pipeline.optimizer.DefaultOptimizerFactory;
import gov.va.sparkcql.pipeline.optimizer.OptimizerFactory;
import gov.va.sparkcql.pipeline.preprocessor.PreprocessorFactory;
import gov.va.sparkcql.pipeline.retriever.RetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.SparkIndexedDataRetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;

import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategyFactory;
import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PipelineTest extends AbstractTest {

    private Configuration configure() {
        var cfg = new MockConfiguration();
        cfg.writeBinding(SparkFactory.class, LocalSparkFactory.class);
        cfg.writeBinding(TableResolutionStrategyFactory.class, TemplateResolutionStrategyFactory.class);
        cfg.writeBinding(OptimizerFactory.class, DefaultOptimizerFactory.class);
        cfg.writeBinding(RetrieverFactory.class, SparkIndexedDataRetrieverFactory.class);
        cfg.writeBinding(ConvergerFactory.class, DefaultConvergerFactory.class);
        cfg.writeBinding(EvaluatorFactory.class, MockEvaluatorFactory.class);
        cfg.writeBinding(ModelAdapterFactory.class, MockModelAdapterFactory.class);
        cfg.writeBinding(PreprocessorFactory.class, MockDataPreprocessorFactory.class);
        return cfg;
    }

    private Injector getInjector() {
        return new Injector(configure());
    }

    @Test
    public void should_initialize_default_components() {
        var pipeline = new Pipeline(configure());
        assertNotNull(pipeline.getOptimizer());
        assertNotNull(pipeline.getEvaluator());
    }

    @Test
    public void should_execute_mock_model() throws IOException {
        var libraryContents = Resources.read("mock-model/elm/mock-library.json");
        var reader = new ElmJsonLibraryReader();
        var plan = new Plan()
                .withLibrary(reader.read(libraryContents));
        var pipeline = new Pipeline(configure());
        var results = pipeline.execute(plan);
        results.splitByContext().collect().forEach(System.out::println);
    }
}