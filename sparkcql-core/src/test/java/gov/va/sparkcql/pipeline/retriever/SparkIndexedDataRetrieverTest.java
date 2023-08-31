package gov.va.sparkcql.pipeline.retriever;

import java.util.List;

import javax.xml.namespace.QName;

import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.fixture.mock.MockDataPreprocessor;
import gov.va.sparkcql.fixture.mock.MockModelAdapter;
import gov.va.sparkcql.fixture.mock.MockConfiguration;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategyFactory;
import org.hl7.elm.r1.Retrieve;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.configuration.ServiceModule;
import gov.va.sparkcql.pipeline.model.ModelAdapterComposite;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SparkIndexedDataRetrieverTest extends ServiceModule {

    @Test
    public void should_load_sample_data() {
        var configuration = new MockConfiguration();
        var modelAdapterComposite = new ModelAdapterComposite(List.of(new MockModelAdapter()));

        var tableResolutionStrategy = new TemplateResolutionStrategy(
                configuration.readSetting(TemplateResolutionStrategyFactory.TEMPLATE_RESOLUTION_STRATEGY)
                        .orElseThrow());
        var dataLoader = new MockDataPreprocessor(getSparkFactory(), tableResolutionStrategy, modelAdapterComposite);
        dataLoader.apply();

        var retriever = new SparkIndexedDataRetriever(getSparkFactory(), tableResolutionStrategy);

        var r1 = new Retrieve().withDataType(new QName("http://va.gov/sparkcql/mock", "MockPatient"));
        var r2 = new Retrieve().withDataType(new QName("http://va.gov/sparkcql/mock", "MockEntity"));
        var rdd1 = retriever.retrieve(Retrieval.of(r1), modelAdapterComposite);
        var rdd2 = retriever.retrieve(Retrieval.of(r2), modelAdapterComposite);
        assertEquals(3, rdd1.count());
        assertEquals(4, rdd2.count());
    }
}