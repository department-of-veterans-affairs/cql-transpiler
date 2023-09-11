package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.domain.RetrieveDefinition;
import gov.va.sparkcql.io.AssetFolder;
import gov.va.sparkcql.pipeline.model.FhirModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import gov.va.sparkcql.types.DataType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BundleRetrieverTest extends AbstractTest {

    @Test
    public void should_retrieve_patients_from_bundled_resources() {
        checkRetrieveForResourceType("Patient", 2);
        checkRetrieveForResourceType("Encounter", 111);
        checkRetrieveForResourceType("Condition", 79);
        checkRetrieveForResourceType("MedicationAdministration", 6);
    }

    private void checkRetrieveForResourceType(String resourceType, int expectedCount) {
        var retrieval = new RetrieveDefinition()
                .withDataType(new DataType("http://hl7.org/fhir", resourceType));
        var modelAdapter = new FhirModelAdapter();
        var modelAdapterSet = new ModelAdapterSet(List.of(modelAdapter));
        var retriever = new BundleRetriever(
                configuration,
                sparkFactory,
                AssetFolder.of("resource://fhir/bundles"));
        var bundles = retriever.retrieve(retrieval, modelAdapterSet);
        assertEquals(expectedCount, bundles.count());
    }
}