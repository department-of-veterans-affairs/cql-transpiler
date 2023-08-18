package gov.va.sparkcql;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.configuration.SystemConfiguration;
import gov.va.sparkcql.configuration.TestConfiguration;
import gov.va.sparkcql.repository.clinical.ClinicalRepository;
import gov.va.sparkcql.repository.clinical.SyntheticFhirEncounterRepository;
import gov.va.sparkcql.repository.cql.CqlSourceFileRepository;
import gov.va.sparkcql.repository.cql.CqlSourceRepository;
import gov.va.sparkcql.service.executor.BulkRetriever;
import gov.va.sparkcql.service.executor.CqfEngine;
import gov.va.sparkcql.service.executor.DefaultExecutor;
import gov.va.sparkcql.service.executor.Engine;
import gov.va.sparkcql.service.executor.Executor;
import gov.va.sparkcql.service.executor.SparkBulkRetriever;
import gov.va.sparkcql.service.modeladapter.FhirModelAdapter;
import gov.va.sparkcql.service.modeladapter.ModelAdapter;
import gov.va.sparkcql.service.planner.DefaultPlanner;
import gov.va.sparkcql.service.planner.Planner;
import gov.va.sparkcql.service.compiler.Compiler;
import gov.va.sparkcql.service.compiler.CqfCompiler;

public class AbstractMeasureTest extends AbstractModule {

    @Override
    protected void configure() {
        var clinicalDataBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<ClinicalRepository<?>>() {});
        clinicalDataBinder.addBinding().to(SyntheticFhirEncounterRepository.class);
        
        var modelAdapterBinder = Multibinder.newSetBinder(binder(), ModelAdapter.class);
        modelAdapterBinder.addBinding().to(FhirModelAdapter.class);

        bind(SystemConfiguration.class).to(TestConfiguration.class);
        bind(Compiler.class).to(CqfCompiler.class);
        bind(CqlSourceRepository.class).to(CqlSourceFileRepository.class);
        bind(Planner.class).to(DefaultPlanner.class);
        bind(SparkFactory.class).to(LocalSparkFactory.class);
        bind(BulkRetriever.class).to(SparkBulkRetriever.class);
        bind(Engine.class).to(CqfEngine.class);
        bind(Executor.class).to(DefaultExecutor.class);
    }

    protected Injector getInjector() {
        return Guice.createInjector(this);
    }
}