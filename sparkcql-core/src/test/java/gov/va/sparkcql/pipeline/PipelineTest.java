package gov.va.sparkcql.pipeline;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.configuration.SystemConfiguration;
import gov.va.sparkcql.fixture.sample.SampleConfiguration;
import gov.va.sparkcql.fixture.sample.SampleEngine;
import gov.va.sparkcql.fixture.sample.SampleModel;
import gov.va.sparkcql.pipeline.combiner.DefaultCombiner;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapter;
import gov.va.sparkcql.pipeline.planner.DefaultPlanner;
import gov.va.sparkcql.pipeline.retriever.SparkBoxEncodedDataRetriever;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategy;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategy;

import org.junit.jupiter.api.Test;

import com.google.inject.multibindings.Multibinder;

import static org.junit.jupiter.api.Assertions.*;

public class PipelineTest extends AbstractTest {

    @Override
    protected void configure() {
        super.configure();
        bind(SystemConfiguration.class).to(SampleConfiguration.class);
        bind(TableResolutionStrategy.class).to(TemplateResolutionStrategy.class);
        var pipelineComponentBinder = Multibinder.newSetBinder(binder(), Component.class);
        pipelineComponentBinder.addBinding().to(DefaultPlanner.class);
        pipelineComponentBinder.addBinding().to(DefaultCombiner.class);
        pipelineComponentBinder.addBinding().to(SparkBoxEncodedDataRetriever.class);
        pipelineComponentBinder.addBinding().to(SampleEngine.class);
        var modelAdapterBinder = Multibinder.newSetBinder(binder(), ModelAdapter.class);
        modelAdapterBinder.addBinding().to(SampleModel.class);
    }

    @Test
    public void should_initialize_default_components() {
        var pipeline = getInjector().getInstance(Pipeline.class);
        assertFalse(pipeline.getComponents().isEmpty());
        assertNotNull(pipeline.getPlanner());
        assertNotNull(pipeline.getEngine());
    }

    @Test
    public void should_load_sample_data() {
        var pipeline = getInjector().getInstance(Pipeline.class);
        pipeline.execute("[\"SampleEntity\": \"Sample Valueset A\"] E");

        // var tableResolutionStrategy = new TemplateResolutionStrategy(new SampleConfiguration());
        // var dataLoader = new SampleDataLoaderPreprocessor(tableResolutionStrategy);
        // dataLoader.apply(pipeline);
        // var retriever = new SparkBoxEncodedDataRetriever(
        //         new LocalSparkFactory(),
        //         tableResolutionStrategy
        //         );
        // var retrievalOp = new RetrievalOperation()
        //         .withRetrieve(new Retrieve()
        //                 .withDataType(new QName("http://va.gov/sparkcql/sample", "SampleEntity")));
        // var r = retriever.retrieve(retrievalOp, new ModelAdapterResolver(Set.of(new SampleModel())));
    }    
}