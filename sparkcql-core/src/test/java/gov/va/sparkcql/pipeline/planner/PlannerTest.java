package gov.va.sparkcql.pipeline.planner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.pipeline.planner.DefaultPlanner;
import gov.va.sparkcql.domain.Plan;

import java.io.IOException;
import java.util.List;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;
import org.hl7.elm.r1.Library;

import static org.junit.jupiter.api.Assertions.*;

public class PlannerTest {
    
    private Library sampleLibrary;
    private Plan plan;

    @BeforeEach
    public void setup() throws IOException {
        var libraryContents = Resources.read("sample/sample-library.json");
        var reader = new ElmJsonLibraryReader();
        this.sampleLibrary = reader.read(libraryContents);
        var planner = new DefaultPlanner();
        this.plan = planner.plan(List.of(sampleLibrary));
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