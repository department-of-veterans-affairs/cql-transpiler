package gov.va.sparkcql.pipeline.optimizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.domain.Plan;

import java.io.IOException;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;
import org.hl7.elm.r1.Library;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultOptimizerTest {
    
    private Library mockLibrary;
    private Plan mockPlan;

    @BeforeEach
    public void setup() throws IOException {
        this.mockLibrary = readElm("mock-model/elm/mock-library.json");
        var optimizer = new DefaultOptimizer();
        this.mockPlan = optimizer.optimize(new Plan().withLibrary(mockLibrary));
    }

    private static Library readElm(String path) throws IOException {
        var contents = Resources.read(path);
        var reader = new ElmJsonLibraryReader();
        return reader.read(contents);
    }

    @Test
    public void should_extract_retrieves() {
        assertEquals(2, mockPlan.getRetrieves().size());
    }

    @Test
    public void should_produce_unique_operation_hashes() {
        var hash1 = mockPlan.getRetrieves().get(0).hashCode();
        var hash2 = mockPlan.getRetrieves().get(1).hashCode();
        assertNotEquals(hash1, hash2);
    }

    @Test
    public void should_deduplicate_retrievals() throws IOException {
        var library = readElm("test-cases/deduplicate-retrieves.json");
        var optimizer = new DefaultOptimizer();
        var plan = optimizer.optimize(new Plan().withLibrary(library));
        assertEquals(2, plan.getRetrieves().size());
    }
}