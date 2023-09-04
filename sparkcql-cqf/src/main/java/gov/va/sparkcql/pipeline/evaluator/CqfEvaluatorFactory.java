package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.model.ModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import org.cqframework.cql.cql2elm.CqlCompilerOptions;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.opencds.cqf.cql.engine.data.DataProvider;
import org.opencds.cqf.cql.engine.execution.CqlEngine;
import org.opencds.cqf.cql.engine.execution.Environment;
import org.opencds.cqf.cql.engine.fhir.model.R4FhirModelResolver;
import org.opencds.cqf.cql.engine.model.ModelResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CqfEvaluatorFactory extends EvaluatorFactory {

    private List<MutableCompositeDataProvider> dataProviders;

    public CqfEvaluatorFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Evaluator create(Plan plan, ModelAdapterSet modelAdapterSet, Object terminologyData) {

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
        var dataProviderMap = buildDataProviders(modelAdapterSet);

        // Terminology adapter for translating bulk terminology data to the CQF engine.
        var terminologyProviderAdapter = new TerminologyProviderAdapter(terminologyData);

        // Setup the environment and engine.
        var environment = new Environment(
                libraryManager,
                dataProviderMap,
                terminologyProviderAdapter);

        var cqlEngine = new CqlEngine(environment, Set.of(CqlEngine.Options.EnableValidation));

        return new CqfEvaluator(cqlEngine, dataProviders, libraryCacheAdapter);
    }

    private Map<String, DataProvider> buildDataProviders(ModelAdapterSet modelAdapterSet) {
        this.dataProviders = new ArrayList<>();
        return modelAdapterSet.getNamespaces().stream()
                .collect(Collectors.toMap(
                        k -> k,
                        v -> {
                            var modelAdapter = modelAdapterSet.forNamespace(v);
                            var modelResolver = resolveModelResolver(modelAdapter);
                            var dataProviderAdapter = new MutableCompositeDataProvider(
                                    modelResolver,
                                    null);     // only resolvable during context execution
                            this.dataProviders.add(dataProviderAdapter);
                            return dataProviderAdapter;
                        }));
    }

    private ModelResolver resolveModelResolver(ModelAdapter modelAdapter) {
        // CQF already provides an implementation for the FHIR ModelResolver so
        // use that instead of implementing and maintaining our own.
        if (modelAdapter.getNamespaceUri().equals("http://hl7.org/fhir")) {
            return new R4FhirModelResolver();
        } else {
            return new ModelResolverAdapter(modelAdapter);
        }
    }
}