package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.pipeline.model.ModelAdapterResolver;
import org.cqframework.cql.cql2elm.CqlCompilerOptions;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.cqframework.cql.cql2elm.model.CompiledLibrary;
import org.hl7.elm.r1.VersionedIdentifier;
import org.opencds.cqf.cql.engine.execution.CqlEngine;
import org.opencds.cqf.cql.engine.execution.Environment;

import java.util.Map;

public class CqfEvaluatorFactory implements EvaluatorFactory {

    @Override
    public Evaluator create(LibraryCollection libraryCollection, ModelAdapterResolver modelAdapterResolver, Object terminologyData) {

        // Adapt the libraries provided to this implementation by the SparkCQL runtime
        // to the CompiledLibrary type required by CQF.
        var libraryCacheAdapter = new LibraryCacheAdapter(libraryCollection);

        // Configure CQF's LibraryManager which is required by the CQF engine to resolve
        // CQL script compilations. However, note that our CQL has already been compiled at
        // this point in the pipeline so our use of LibraryManager is merely a broker to pass
        // along the compiled libraries to the engine.
        var modelManager = new ModelManager();
        var libraryManager = new LibraryManager(
                modelManager,
                CqlCompilerOptions.defaultOptions(),
                libraryCacheAdapter.getVersionedIdentifierToCompiledLibraryMap());

        //
        var dataProviderAdapter = new DataProviderAdapter(modelAdapterResolver);
        TerminologyProviderAdapter terminologyProviderAdapter = null;
        var environment = new Environment(
                libraryManager,
                dataProviderAdapter.getModelUriToDataProviderMap(),
                terminologyProviderAdapter);

        var cqlEngine = new CqlEngine(environment);

        return new CqfEvaluator(cqlEngine, dataProviderAdapter, libraryCacheAdapter);
    }
}