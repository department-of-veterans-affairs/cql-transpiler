package gov.va.sparkcql.pipeline;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.fixture.mock.MockDataPreprocessor;
import gov.va.sparkcql.fixture.mock.MockEvaluator;
import gov.va.sparkcql.fixture.mock.MockModelAdapter;
import gov.va.sparkcql.fixture.mock.MockConfiguration;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.converger.DefaultConverger;
import gov.va.sparkcql.pipeline.model.ModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterResolver;
import gov.va.sparkcql.pipeline.optimizer.DefaultOptimizer;
import gov.va.sparkcql.pipeline.retriever.SparkIndexedDataRetriever;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategy;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;
import org.junit.jupiter.api.Test;

import com.google.inject.multibindings.Multibinder;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PipelineTest extends AbstractTest {

    @Override
    protected void configure() {
        super.configure();
        bind(EnvironmentConfiguration.class).to(MockConfiguration.class);
        bind(TableResolutionStrategy.class).to(TemplateResolutionStrategy.class);
        var pipelineComponentBinder = Multibinder.newSetBinder(binder(), Component.class);
        pipelineComponentBinder.addBinding().to(MockDataPreprocessor.class);
        pipelineComponentBinder.addBinding().to(ModelAdapterResolver.class);
        pipelineComponentBinder.addBinding().to(DefaultOptimizer.class);
        pipelineComponentBinder.addBinding().to(DefaultConverger.class);
        pipelineComponentBinder.addBinding().to(SparkIndexedDataRetriever.class);
        pipelineComponentBinder.addBinding().to(MockEvaluator.class);
        var modelAdapterBinder = Multibinder.newSetBinder(binder(), ModelAdapter.class);
        modelAdapterBinder.addBinding().to(MockModelAdapter.class);
    }

    @Test
    public void should_initialize_default_components() {
        var pipeline = getInjector().getInstance(Pipeline.class);
        assertFalse(pipeline.getComponents().isEmpty());
        assertNotNull(pipeline.getOptimizer());
        assertNotNull(pipeline.getEvaluator());
    }

    @Test
    public void should_execute_mock_model() throws IOException {
        var libraryContents = Resources.read("mock-model/elm/mock-library.json");
        var reader = new ElmJsonLibraryReader();
        var plan = new Plan()
                .withLibrary(reader.read(libraryContents));
        var pipeline = getInjector().getInstance(Pipeline.class);
        var results = pipeline.execute(plan);
        results.splitByContext().collect().forEach(System.out::println);
    }
}