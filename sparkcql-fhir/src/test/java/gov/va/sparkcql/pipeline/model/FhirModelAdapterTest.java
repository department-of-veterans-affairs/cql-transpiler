package gov.va.sparkcql.pipeline.model;

import org.hl7.elm.r1.ContextDef;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FhirModelAdapterTest {

    @Test
    public void should_resolve_context() {
        var patient = new Patient().setId("12345");
        var ref = new Reference(patient);
        checkContextId(new Encounter().setSubject(ref));
        checkContextId(new Condition().setSubject(ref));
        checkContextId(new Claim().setPatient(ref));
        // TODO: Add other types
    }

    private void checkContextId(IBaseResource resource) {
        var modelAdapter = new FhirModelAdapter();
        var contextDef = new ContextDef().withName("Patient");
        var contextId = modelAdapter.getContextId(resource, contextDef);
        assertEquals("12345", contextId);
    }
}