package gov.va.sparkcql.executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.common.di.ServiceContext;
import gov.va.sparkcql.common.io.Resources;
import gov.va.sparkcql.entity.LibraryCollection;
import gov.va.sparkcql.entity.Plan;
import gov.va.sparkcql.planner.Planner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;
import org.hl7.elm.r1.Library;

public class ExecutorTest {
    
    private LibraryCollection libraryCollection;
    private Plan plan;
    

    @BeforeEach
    public void setup() throws IOException {
        var libraryContents = Resources.read("sample/sample-library.json");
        var reader = new ElmJsonLibraryReader();
        this.libraryCollection = new LibraryCollection();
        this.libraryCollection.add(reader.read(libraryContents));
        var planner = ServiceContext.createOne(Planner.class);
        this.plan = planner.plan(this.libraryCollection.stream().toList());
    }

    @Test
    public void should_acquire_required_terminology() {
    }

    @Test
    public void should_retrieve_required_data() {
        var retriever = new SparkBulkRetriever();
        var ds = retriever.retrieve(plan, null);
        assertEquals(ds.count(), 3);
        assertEquals((String)ds.sort("patientCorrelationId").first().getAs("patientCorrelationId"), "1");
    }

    @Test
    public void should_execute_sample() {
        var retriever = ServiceContext.createOne(BulkRetriever.class);
        var clinicalDs = retriever.retrieve(plan, null);
        var executor = new DefaultExecutor();
        var results = executor.execute(this.libraryCollection, this.plan, null, clinicalDs, null);
        var x = results.collectAsList();
        results.show();
    }
}