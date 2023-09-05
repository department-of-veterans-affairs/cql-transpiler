package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.ExpressionReference;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.pipeline.model.ModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import org.cqframework.cql.cql2elm.CqlCompilerOptions;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.hl7.elm.r1.Library;
import org.opencds.cqf.cql.engine.data.CompositeDataProvider;
import org.opencds.cqf.cql.engine.data.DataProvider;
import org.opencds.cqf.cql.engine.execution.CqlEngine;
import org.opencds.cqf.cql.engine.execution.Environment;
import org.opencds.cqf.cql.engine.execution.EvaluationResult;
import org.opencds.cqf.cql.engine.fhir.model.R4FhirModelResolver;
import org.opencds.cqf.cql.engine.model.ModelResolver;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CqfEvaluator implements Evaluator {

    private final Plan plan;
    private final ModelAdapterSet modelAdapterSet;
    private final TerminologyProviderAdapter terminologyProviderAdapter;
    private final LibraryManager libraryManager;
    private final LibraryCacheAdapter libraryCacheAdapter;

    public CqfEvaluator(Plan plan, ModelAdapterSet modelAdapterSet, Object terminologyData) {

        this.plan = plan;
        this.modelAdapterSet = modelAdapterSet;

        // Adapt the libraries provided to this implementation by the SparkCQL runtime
        // to the CompiledLibrary type required by CQF.
        this.libraryCacheAdapter = new LibraryCacheAdapter(plan);

        // Configure CQF's LibraryManager which is required by the CQF engine to resolve
        // CQL script compilations. However, note that our CQL has already been compiled at
        // this point in the pipeline so our use of LibraryManager is merely a broker to pass
        // along the compiled libraries to the engine.
        var modelManager = new ModelManager();
        this.libraryManager = new LibraryManager(
                modelManager,
                CqlCompilerOptions.defaultOptions(),
                libraryCacheAdapter.getVersionedIdentifierToCompiledLibraryMap());

        // Terminology adapter for translating bulk terminology data to the CQF engine.
        this.terminologyProviderAdapter = new TerminologyProviderAdapter(terminologyData);
    }

    @Override
    public EvaluatedContext evaluate(String contextElementId, Map<Retrieval, List<Object>> clinicalData) {

        // The DataProvider adapter combines Model and Retriever, and is a dependent of
        // Environment, so it must be recreated each row execution.
        var dataProviderMap = buildDataProviders(modelAdapterSet, clinicalData);

        // Set up the environment and a new CqlEngine good for one context instance evaluation.
        var environment = new Environment(
                libraryManager,
                dataProviderMap,
                terminologyProviderAdapter);

        var cqlEngine = new CqlEngine(environment, Set.of(CqlEngine.Options.EnableValidation));

        // Evaluate all libraries and return the results conforming to the SparkCQL result type.
        // The CQF engine only accepts a single library for execution so evaluation of an entire
        // plan consisting of multiple libraries requires iteration.
        var exprResults = this.plan.getLibraries().stream().flatMap(library -> {
            var result = cqlEngine.evaluate(library.getIdentifier());

            return result.expressionResults.entrySet().stream().map(entry -> {
                var exprRef = new ExpressionReference()
                        .withLibraryIdentifier(library)
                        .withExpressionDefName(entry.getKey());
                return Tuple2.apply(exprRef, List.of(entry.getValue().value()));
            });
        }).collect(Collectors.toList());

        return new EvaluatedContext()
                .withContextId(contextElementId)
                .withExpressionResults(exprResults);
    }

    private Map<String, DataProvider> buildDataProviders(ModelAdapterSet modelAdapterSet, Map<Retrieval, List<Object>> clinicalData) {
        return modelAdapterSet.getNamespaces().stream()
                .collect(Collectors.toMap(
                        k -> k,
                        v -> {
                            var modelAdapter = modelAdapterSet.forNamespace(v);
                            var modelResolver = resolveModelResolver(modelAdapter);
                            var retrieveProvider = new RetrieveProviderAdapter(clinicalData);
                            return new CompositeDataProvider(
                                    modelResolver,
                                    retrieveProvider);
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