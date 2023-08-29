package gov.va.sparkcql.pipeline.retriever;

import java.util.Set;

import javax.xml.namespace.QName;

import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.fixture.mock.MockDataPreprocessor;
import gov.va.sparkcql.fixture.mock.MockModelAdapter;
import gov.va.sparkcql.fixture.mock.MockConfiguration;
import org.hl7.elm.r1.Retrieve;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterResolver;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SparkIndexedDataRetrieverTest extends AbstractTest {

    @Test
    public void should_load_sample_data() {
        var sparkFactory = new LocalSparkFactory();
        var tableResolutionStrategy = new TemplateResolutionStrategy(new MockConfiguration());
        var dataLoader = new MockDataPreprocessor(sparkFactory, tableResolutionStrategy);
        dataLoader.apply();

        var retriever = new SparkIndexedDataRetriever(sparkFactory, tableResolutionStrategy);

        var r1 = new Retrieve().withDataType(new QName("http://va.gov/sparkcql/mock", "MockPatient"));
        var r2 = new Retrieve().withDataType(new QName("http://va.gov/sparkcql/mock", "MockEntity"));
        var rdd1 = retriever.retrieve(Retrieval.of(r1), new ModelAdapterResolver(Set.of(new MockModelAdapter())));
        var rdd2 = retriever.retrieve(Retrieval.of(r2), new ModelAdapterResolver(Set.of(new MockModelAdapter())));
        assertEquals(3, rdd1.count());
        assertEquals(4, rdd2.count());
    }
}