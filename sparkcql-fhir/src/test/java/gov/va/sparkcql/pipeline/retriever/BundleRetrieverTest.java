package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.io.Asset;
import gov.va.sparkcql.pipeline.model.FhirModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterComposite;
import gov.va.sparkcql.types.DataType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BundleRetrieverTest {

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
        var modelAdapterComposite = new ModelAdapterComposite(List.of(modelAdapter));
        var retriever = new BundleRetriever(new LocalSparkFactory(), Asset.of("classpath://bundles"));
        var bundles = retriever.retrieve(retrieval, modelAdapterComposite);
        assertEquals(expectedCount, bundles.count());
    }
}