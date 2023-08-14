package gov.va.sparkcql.executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.common.io.Resources;
import gov.va.sparkcql.entity.LibraryCollection;
import gov.va.sparkcql.entity.Plan;
import gov.va.sparkcql.planner.DefaultPlanner;
import gov.va.sparkcql.planner.Planner;
import gov.va.sparkcql.repository.ClinicalDataRepositoryFactory;
import gov.va.sparkcql.repository.SampleDataRepositoryFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import com.google.inject.Guice;
import com.google.inject.multibindings.Multibinder;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;

public class ExecutorTest extends AbstractTest {
    
    @Override
    protected void configure() {
        super.configure();
        bind(BulkRetriever.class).to(SparkBulkRetriever.class);
        bind(ClinicalDataRepositoryFactory.class).to(SampleDataRepositoryFactory.class);
        var multibinder = Multibinder.newSetBinder(binder(), BulkRetriever.class);
        multibinder.addBinding().to(SparkBulkRetriever.class);
        bind(Executor.class).to(DefaultExecutor.class);
    }

    private LibraryCollection libraryCollection;
    private Plan plan;
    

    @BeforeEach
    public void setup() throws IOException {
        var libraryContents = Resources.read("sample/sample-library.json");
        var reader = new ElmJsonLibraryReader();
        this.libraryCollection = new LibraryCollection();
        this.libraryCollection.add(reader.read(libraryContents));
        var planner = new DefaultPlanner();
        this.plan = planner.plan(this.libraryCollection.stream().toList());
    }

    @Test
    public void should_acquire_required_terminology() {
    }

    // @Test
    // public void should_retrieve_required_data() {
    //     var retriever = new SparkBulkRetriever();
    //     var ds = retriever.retrieve(plan, null);
    //     assertEquals(ds.count(), 3);
    //     assertEquals((String)ds.sort("patientCorrelationId").first().getAs("patientCorrelationId"), "1");
    // }

    @Test
    public void should_execute_sample() {
        var injector = Guice.createInjector(this);
        var retriever = injector.getInstance(BulkRetriever.class);
        var executor = injector.getInstance(Executor.class);
        var clinicalDs = retriever.retrieve(plan, null);
        var results = executor.execute(this.libraryCollection, this.plan, null, null, clinicalDs, null);
        var x = results.collectAsList();
        results.show();
    }
}