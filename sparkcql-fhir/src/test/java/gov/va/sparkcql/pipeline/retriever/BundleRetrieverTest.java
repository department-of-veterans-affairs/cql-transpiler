package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.pipeline.model.FhirModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterResolver;
import gov.va.sparkcql.types.DataType;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BundleRetrieverTest {

    @Test
    public void should_retrieve_patients_from_bundled_resources() {
        checkRetrieveForResourceType("Patient", 20);
        checkRetrieveForResourceType("Encounter", 4006);
        checkRetrieveForResourceType("Condition", 1844);
    }

    private void checkRetrieveForResourceType(String resourceType, int expectedCount) {
        var retrieval = new Retrieval()
                .withDataType(new DataType("http://hl7.org/fhir", resourceType));
        var modelAdapter = new FhirModelAdapter();
        var modelAdapterResolver = new ModelAdapterResolver(Set.of(modelAdapter));
        var retriever = new BundleRetriever(new LocalSparkFactory(), "bundles");
        var bundles = retriever.retrieve(retrieval, modelAdapterResolver);
        assertEquals(expectedCount, bundles.count());
    }
}