package gov.va.sparkcql;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.cqframework.cql.elm.serializing.ElmLibraryWriterFactory;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.Multibinder;

import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.configuration.SystemConfiguration;
import gov.va.sparkcql.configuration.TestConfiguration;
import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.pipeline.compiler.Compiler;
import gov.va.sparkcql.pipeline.engine.Engine;
import gov.va.sparkcql.pipeline.executor.BulkRetriever;
import gov.va.sparkcql.pipeline.executor.DefaultExecutor;
import gov.va.sparkcql.pipeline.executor.Executor;
import gov.va.sparkcql.pipeline.executor.SparkBulkRetriever;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapter;
import gov.va.sparkcql.pipeline.planner.DefaultPlanner;
import gov.va.sparkcql.pipeline.planner.Planner;
import gov.va.sparkcql.pipeline.repository.clinical.ClinicalRepository;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepository;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;
import gov.va.sparkcql.repository.clinical.SyntheticFhirEncounterRepository;
import gov.va.sparkcql.repository.clinical.SyntheticFhirPatientRepository;
import gov.va.sparkcql.repository.clinical.SyntheticFhirConditionRepository;
import gov.va.sparkcql.service.executor.CqfEngine;
import gov.va.sparkcql.service.modeladapter.FhirModelAdapter;
import gov.va.sparkcql.service.compiler.CqfCompiler;
import scala.Function1;
import scala.collection.Iterator;

public class AbstractMeasureTest extends AbstractModule implements Serializable {

    @Override
    protected void configure() {
        var clinicalDataBinder = Multibinder.newSetBinder(binder(), new TypeLiteral<ClinicalRepository<?>>() {});
        clinicalDataBinder.addBinding().to(SyntheticFhirPatientRepository.class);
        clinicalDataBinder.addBinding().to(SyntheticFhirConditionRepository.class);
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

    protected void execMeasures(VersionedIdentifier... libraryIdentifiers) {
        // Compilation Phase
        var compiler = getInjector().getInstance(Compiler.class);
        var libraries = compiler.compile(List.of(libraryIdentifiers));
        write(libraries, "./.temp/");

        // Planning Phase
        var planner = getInjector().getInstance(Planner.class);
        var plan = planner.plan(libraries);
        
        // Retrieval Phase
        var retriever = getInjector().getInstance(BulkRetriever.class);
        var executor = getInjector().getInstance(Executor.class);
        var clinicalDs = retriever.retrieve(plan, null);

        // Calculation Phase
        var results = executor.execute(new LibraryCollection(libraries), plan, clinicalDs, null);
        try {
            results.count();
            // var testDs = clinicalDs.mapPartitions((Function1<scala.collection.Iterator<Row>, Iterator<Row>>) input -> input, RowEncoder.apply(clinicalDs.schema()));
            // testDs.show();
            //new RunExecutor().run(clinicalDs);
        } catch (Exception e) {
            System.out.println(e.getMessage().substring(0, 1000));
        }
        // var x = results.collectAsList();
        // results.show(10, false);

//         var parameter = sparkcql.parameter("Measurement Period").dateTimeInterval("2013-01-01", "2014-01-01")
//         assertEvaluation(evaluation)
//         diagnoseEvaluation(evaluation)
    }

    public static void write(List<Library> libraries, String targetPath) {
        libraries.forEach(library -> {
            var name = java.util.UUID.randomUUID().toString();
            if (library.getIdentifier().getId() != null) {
                name = library.getIdentifier().getId();
            }

            try {
                Files.createDirectories(Paths.get(targetPath));
                var writer = new FileWriter(new File(targetPath + name + ".json"));
                ElmLibraryWriterFactory.getWriter("application/elm+json").write(library, writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}