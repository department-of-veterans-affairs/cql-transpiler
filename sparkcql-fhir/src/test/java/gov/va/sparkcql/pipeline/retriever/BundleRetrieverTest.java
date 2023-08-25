package gov.va.sparkcql.pipeline.retriever;

        import gov.va.sparkcql.configuration.LocalSparkFactory;
        import gov.va.sparkcql.domain.Retrieval;
        import gov.va.sparkcql.pipeline.modeladapter.FhirModelAdapter;
        import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;
        import gov.va.sparkcql.types.DataType;
        import org.hl7.elm.r1.ContextDef;
        import org.hl7.fhir.instance.model.api.IBaseResource;
        import org.hl7.fhir.r4.model.*;
        import org.junit.jupiter.api.Test;

        import java.util.List;
        import java.util.Set;

        import static org.junit.jupiter.api.Assertions.assertEquals;

public class BundleRetrieverTest {

    @Test
    public void should_retrieve_patients_from_bundled_resources() {
        checkRetrieveForResourceType("Patient", 10);
        checkRetrieveForResourceType("Encounter", 404);
        checkRetrieveForResourceType("Condition", 94);
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
    @Test
    public void should_resolve_context() {
        var patient = new Patient().setId("12345");
        var ref = new Reference(patient);
        checkContextId(new Encounter().setSubject(ref));
        checkContextId(new Condition().setSubject(ref));
        checkContextId(new Claim().setPatient(ref));
    }

    private void checkContextId(IBaseResource resource) {
        var modelAdapter = new FhirModelAdapter();
        var contextDef = new ContextDef().withName("Patient");
        var contextId = modelAdapter.getContextId(resource, contextDef);
        assertEquals("12345", contextId);
    }
}