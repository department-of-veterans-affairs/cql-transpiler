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
    private Plan plan;

    @BeforeEach
    public void setup() throws IOException {
        var libraryContents = Resources.read("mock-model/elm/mock-library.json");
        var reader = new ElmJsonLibraryReader();
        this.mockLibrary = reader.read(libraryContents);
        var optimizer = new DefaultOptimizer();
        this.plan = optimizer.optimize(new Plan().withLibrary(mockLibrary));
    }

    @Test
    public void should_extract_retrieves() {
        assertEquals(2, plan.getRetrieves().size());
    }

    @Test
    public void should_produce_unique_operation_hashes() {
        var hash1 = plan.getRetrieves().get(0).hashCode();
        var hash2 = plan.getRetrieves().get(1).hashCode();
        assertNotEquals(hash1, hash2);
    }    
}