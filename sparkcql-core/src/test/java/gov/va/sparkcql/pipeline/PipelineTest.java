package gov.va.sparkcql.pipeline;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.configuration.SystemConfiguration;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.fixture.SampleConfiguration;
import gov.va.sparkcql.fixture.SampleDataPreprocessor;
import gov.va.sparkcql.fixture.SampleEvaluator;
import gov.va.sparkcql.fixture.SampleModelAdapter;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.converger.DefaultConverger;
import gov.va.sparkcql.pipeline.model.ModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterResolver;
import gov.va.sparkcql.pipeline.optimizer.DefaultOptimizer;
import gov.va.sparkcql.pipeline.retriever.SparkBoxedDataRetriever;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategy;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;
import org.junit.jupiter.api.Test;

import com.google.inject.multibindings.Multibinder;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PipelineTest extends AbstractTest {

    @Override
    protected void configure() {
        super.configure();
        bind(SystemConfiguration.class).to(SampleConfiguration.class);
        bind(TableResolutionStrategy.class).to(TemplateResolutionStrategy.class);
        var pipelineComponentBinder = Multibinder.newSetBinder(binder(), Component.class);
        pipelineComponentBinder.addBinding().to(SampleDataPreprocessor.class);
        pipelineComponentBinder.addBinding().to(ModelAdapterResolver.class);
        pipelineComponentBinder.addBinding().to(DefaultOptimizer.class);
        pipelineComponentBinder.addBinding().to(DefaultConverger.class);
        pipelineComponentBinder.addBinding().to(SparkBoxedDataRetriever.class);
        pipelineComponentBinder.addBinding().to(SampleEvaluator.class);
        var modelAdapterBinder = Multibinder.newSetBinder(binder(), ModelAdapter.class);
        modelAdapterBinder.addBinding().to(SampleModelAdapter.class);
    }

    @Test
    public void should_initialize_default_components() {
        var pipeline = getInjector().getInstance(Pipeline.class);
        assertFalse(pipeline.getComponents().isEmpty());
        assertNotNull(pipeline.getOptimizer());
        assertNotNull(pipeline.getEvaluator());
    }

    @Test
    public void should_execute_sample() throws IOException {
        var libraryContents = Resources.read("sample/sample-library.json");
        var reader = new ElmJsonLibraryReader();
        var plan = new Plan()
                .withLibrary(reader.read(libraryContents));
        var pipeline = getInjector().getInstance(Pipeline.class);
        var results = pipeline.execute(plan);
        results.splitByContext().collect().forEach(System.out::println);
    }
}