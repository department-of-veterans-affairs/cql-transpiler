package gov.va.sparkcql.service.executor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.adapter.SampleModel;
import gov.va.sparkcql.io.Resources;
import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.repository.clinical.ClinicalRepository;
import gov.va.sparkcql.repository.clinical.SampleDomainClinicalRepository;
import gov.va.sparkcql.repository.clinical.SamplePatientClinicalRepository;
import gov.va.sparkcql.repository.resolution.NoneResolutionStrategy;
import gov.va.sparkcql.repository.resolution.TableResolutionStrategy;
import gov.va.sparkcql.service.modeladapter.ModelAdapter;
import gov.va.sparkcql.service.planner.DefaultPlanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import com.google.inject.multibindings.Multibinder;
import com.google.inject.TypeLiteral;

import org.cqframework.cql.elm.serializing.jackson.ElmJsonLibraryReader;

public class ExecutorTest extends AbstractTest {
    
    @Override
    protected void configure() {
        super.configure();
        var clinicalDataBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<ClinicalRepository<?>>() {});
        clinicalDataBinder.addBinding().to(SamplePatientClinicalRepository.class);
        clinicalDataBinder.addBinding().to(SampleDomainClinicalRepository.class);
        var modelAdapterBinder = Multibinder.newSetBinder(binder(), ModelAdapter.class);
        modelAdapterBinder.addBinding().to(SampleModel.class);
        bind(TableResolutionStrategy.class).to(NoneResolutionStrategy.class);
        bind(BulkRetriever.class).to(SparkBulkRetriever.class);
        bind(Engine.class).to(SampleEngine.class);
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

    @Test
    public void should_retrieve_required_data() {
        var retriever = getInjector().getInstance(BulkRetriever.class);
        var ds = retriever.retrieve(plan, null);
        assertEquals(ds.count(), 3);
        assertEquals((String)ds.sort("contextCorrelationId").first().getAs("contextCorrelationId"), "1");
    }

    @Test
    public void should_execute_sample() {
        var retriever = getInjector().getInstance(BulkRetriever.class);
        var executor = getInjector().getInstance(Executor.class);
        var clinicalDs = retriever.retrieve(plan, null);
        var results = executor.execute(this.libraryCollection, this.plan, clinicalDs, null);
        var x = results.collectAsList();
        results.show(10, false);
    }
}