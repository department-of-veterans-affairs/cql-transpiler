package gov.va.sparkcql.planner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.common.di.ServiceContext;
import gov.va.sparkcql.common.io.Resources;
import gov.va.sparkcql.model.Plan;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;
import org.hl7.elm.r1.Library;

public class PlannerTest {
    
    private Library sampleLibrary;
    private Plan plan;

    @BeforeEach
    public void setup() throws IOException {
        var libraryContents = Resources.read("sample/sample-library.json");
        var reader = new ElmJsonLibraryReader();
        this.sampleLibrary = reader.read(libraryContents);
        var planner = ServiceContext.createOne(Planner.class);
        this.plan = planner.plan(List.of(sampleLibrary));
    }

    @Test
    public void should_extract_retrieves() {
        assertTrue(plan.getRetrievalOperations().size() == 2);
    }

    @Test
    public void should_produce_unique_operation_hashes() {
        var hash1 = plan.getRetrievalOperations().get(0).getHashKey();
        var hash2 = plan.getRetrievalOperations().get(1).getHashKey();
        assertNotEquals(hash1, hash2);
    }    
}