package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.model.ModelAdapterComposite;
import org.cqframework.cql.cql2elm.CqlCompilerOptions;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.opencds.cqf.cql.engine.execution.CqlEngine;
import org.opencds.cqf.cql.engine.execution.Environment;

public class CqfEvaluatorFactory extends EvaluatorFactory {

    public CqfEvaluatorFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Evaluator create(Plan plan, ModelAdapterComposite modelAdapterComposite, Object terminologyData) {

        // Adapt the libraries provided to this implementation by the SparkCQL runtime
        // to the CompiledLibrary type required by CQF.
        var libraryCacheAdapter = new LibraryCacheAdapter(plan);

        // Configure CQF's LibraryManager which is required by the CQF engine to resolve
        // CQL script compilations. However, note that our CQL has already been compiled at
        // this point in the pipeline so our use of LibraryManager is merely a broker to pass
        // along the compiled libraries to the engine.
        var modelManager = new ModelManager();
        var libraryManager = new LibraryManager(
                modelManager,
                CqlCompilerOptions.defaultOptions(),
                libraryCacheAdapter.getVersionedIdentifierToCompiledLibraryMap());

        // The DataProvider adapter can only be partially initialized since data for each
        // row is only available during row execution. However, DataProvider is a requirement
        // for the CQF environment so we configure a mutable adapter which will be updated
        // during row execution.
        var dataProviderAdapter = new DataProviderAdapter(modelAdapterComposite);

        // Terminology adapter for translating bulk terminology data to the CQF engine.
        var terminologyProviderAdapter = new TerminologyProviderAdapter(terminologyData);

        // Setup the environment and engine.
        var environment = new Environment(
                libraryManager,
                dataProviderAdapter.getDataProviderMap(),
                terminologyProviderAdapter);

        var cqlEngine = new CqlEngine(environment);

        return new CqfEvaluator(cqlEngine, dataProviderAdapter, libraryCacheAdapter);
    }
}