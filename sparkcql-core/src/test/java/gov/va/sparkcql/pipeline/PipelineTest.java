package gov.va.sparkcql.pipeline;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.configuration.SystemConfiguration;
import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.fixture.sample.SampleConfiguration;
import gov.va.sparkcql.fixture.sample.SampleDataPreprocessor;
import gov.va.sparkcql.fixture.sample.SampleEvaluator;
import gov.va.sparkcql.fixture.sample.SampleModel;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.combiner.DefaultCombiner;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapter;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;
import gov.va.sparkcql.pipeline.planner.DefaultPlanner;
import gov.va.sparkcql.pipeline.retriever.SparkBoxEncodedDataRetriever;
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
        bind(SystemConfiguration.class).to(SampleConfiguration.class);
        bind(TableResolutionStrategy.class).to(TemplateResolutionStrategy.class);
        var pipelineComponentBinder = Multibinder.newSetBinder(binder(), Component.class);
        pipelineComponentBinder.addBinding().to(SampleDataPreprocessor.class);
        pipelineComponentBinder.addBinding().to(ModelAdapterResolver.class);
        pipelineComponentBinder.addBinding().to(DefaultPlanner.class);
        pipelineComponentBinder.addBinding().to(DefaultCombiner.class);
        pipelineComponentBinder.addBinding().to(SparkBoxEncodedDataRetriever.class);
        pipelineComponentBinder.addBinding().to(SampleEvaluator.class);
        var modelAdapterBinder = Multibinder.newSetBinder(binder(), ModelAdapter.class);
        modelAdapterBinder.addBinding().to(SampleModel.class);
    }

    @Test
    public void should_initialize_default_components() {
        var pipeline = getInjector().getInstance(Pipeline.class);
        assertFalse(pipeline.getComponents().isEmpty());
        assertNotNull(pipeline.getPlanner());
        assertNotNull(pipeline.getEvaluator());
    }

    @Test
    public void should_execute_sample() throws IOException {
        var libraryContents = Resources.read("sample/sample-library.json");
        var reader = new ElmJsonLibraryReader();
        var libraryCollection = new LibraryCollection();
        libraryCollection.add(reader.read(libraryContents));
        var pipeline = getInjector().getInstance(Pipeline.class);
        var results = pipeline.execute(libraryCollection);
        results.splitByContext().collect().forEach(System.out::println);
    }
}