package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.configuration.ServiceModule;
import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.io.AssetFolder;
import gov.va.sparkcql.pipeline.model.FhirModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterCollection;
import gov.va.sparkcql.types.DataType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BundleRetrieverTest extends ServiceModule {

    @Test
    public void should_retrieve_patients_from_bundled_resources() {
        checkRetrieveForResourceType("Patient", 2);
        checkRetrieveForResourceType("Encounter", 111);
        checkRetrieveForResourceType("Condition", 79);
        checkRetrieveForResourceType("MedicationAdministration", 6);
    }

    private void checkRetrieveForResourceType(String resourceType, int expectedCount) {
        var retrieval = new Retrieval()
                .withDataType(new DataType("http://hl7.org/fhir", resourceType));
        var modelAdapter = new FhirModelAdapter();
        var modelAdapterCollection = new ModelAdapterCollection(List.of(modelAdapter));
        var retriever = new BundleRetriever(getSparkFactory(), AssetFolder.of("resource://fhir/bundles"));
        var bundles = retriever.retrieve(retrieval, modelAdapterCollection);
        assertEquals(expectedCount, bundles.count());
    }
}