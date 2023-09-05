package gov.va.sparkcql.pipeline;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.runtime.LocalSparkFactory;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.fixture.mock.*;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.compiler.CompilerFactory;
import gov.va.sparkcql.pipeline.converger.ConvergerFactory;
import gov.va.sparkcql.pipeline.converger.DefaultConvergerFactory;
import gov.va.sparkcql.pipeline.evaluator.EvaluatorFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterFactory;
import gov.va.sparkcql.pipeline.optimizer.DefaultOptimizerFactory;
import gov.va.sparkcql.pipeline.optimizer.OptimizerFactory;
import gov.va.sparkcql.pipeline.preprocessor.PreprocessorFactory;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepositoryFactory;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepositoryFactory;
import gov.va.sparkcql.pipeline.retriever.RetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.SparkIndexedDataRetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;

import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategyFactory;
import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PipelineTest extends AbstractTest {

    public PipelineTest() {
        this.configuration
            .writeBinding(SparkFactory.class, LocalSparkFactory.class)
            .writeBinding(CqlSourceRepositoryFactory.class, CqlSourceFileRepositoryFactory.class)
            .writeBinding(CompilerFactory.class, MockCompilerFactory.class)
            .writeBinding(TableResolutionStrategyFactory.class, TemplateResolutionStrategyFactory.class)
            .writeBinding(OptimizerFactory.class, DefaultOptimizerFactory.class)
            .writeBinding(RetrieverFactory.class, SparkIndexedDataRetrieverFactory.class)
            .writeBinding(ConvergerFactory.class, DefaultConvergerFactory.class)
            .writeBinding(EvaluatorFactory.class, MockEvaluatorFactory.class)
            .writeBinding(ModelAdapterFactory.class, MockModelAdapterFactory.class)
            .writeBinding(PreprocessorFactory.class, MockDataPreprocessorFactory.class);
    }

    private Injector getInjector() {
        return new Injector(this.configuration);
    }

    @Test
    public void should_initialize_default_components() {
        var pipeline = new Pipeline(this.configuration);
        assertNotNull(pipeline.getOptimizer());
        assertNotNull(pipeline.getCompiler());
    }

    @Test
    public void should_execute_mock_model() throws IOException {
        var libraryContents = Resources.read("mock-model/elm/mock-library.json");
        var reader = new ElmJsonLibraryReader();
        var plan = new Plan()
                .withLibrary(reader.read(libraryContents));
        var pipeline = new Pipeline(this.configuration);
        var results = pipeline.execute(plan);
        results.splitByContext().collect().forEach(System.out::println);
    }
}