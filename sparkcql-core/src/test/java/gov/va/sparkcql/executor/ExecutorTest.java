package gov.va.sparkcql.executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.common.di.ServiceContext;
import gov.va.sparkcql.common.io.Resources;
import gov.va.sparkcql.model.Plan;
import gov.va.sparkcql.planner.Planner;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;

public class ExecutorTest {
    
    private Plan plan;

    @BeforeEach
    public void setup() throws IOException {
        var libraryContents = Resources.read("sample/sample-library.json");
        var reader = new ElmJsonLibraryReader();
        var sampleLibrary = reader.read(libraryContents);
        var planner = ServiceContext.createOne(Planner.class);
        this.plan = planner.plan(List.of(sampleLibrary));
    }

    @Test
    public void should_acquire_required_terminology() {
    }

    @Test
    public void should_retrieve_required_data() {
        var retriever = new SparkBulkRetriever();
        retriever.retrieve(plan, null);
    }

    @Test
    public void should_execute_sample() {
        assertTrue(true);
    }
}