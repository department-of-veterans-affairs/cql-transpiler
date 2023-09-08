package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.ExpressionReference;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.domain.RetrieveDefinition;
import gov.va.sparkcql.pipeline.model.ModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import org.cqframework.cql.cql2elm.CqlCompilerOptions;
import org.cqframework.cql.cql2elm.LibraryManager;
import org.cqframework.cql.cql2elm.ModelManager;
import org.opencds.cqf.cql.engine.data.CompositeDataProvider;
import org.opencds.cqf.cql.engine.data.DataProvider;
import org.opencds.cqf.cql.engine.execution.CqlEngine;
import org.opencds.cqf.cql.engine.execution.Environment;
import org.opencds.cqf.cql.engine.fhir.model.R4FhirModelResolver;
import org.opencds.cqf.cql.engine.model.ModelResolver;
import org.opencds.cqf.cql.engine.runtime.Tuple;
import scala.Tuple2;

import java.util.*;
import java.util.stream.Collectors;

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
    public EvaluatedContext evaluate(String contextElementId, Map<RetrieveDefinition, List<Object>> clinicalData) {

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

                return Tuple2.apply(
                        exprRef,
                        convertResultValue(entry.getValue().value()));
            });
        }).collect(Collectors.toList());

        return new EvaluatedContext()
                .withContextId(contextElementId)
                .withExpressionResults(exprResults);
    }

    private List<Object> convertResultValue(Object value) {
        // At the root level, a value from the Engine is typically a list of
        // objects, each representing a row in the result set.

        // If an iterable, collect it as an array to simplify logic. Since the evaluation design
        // buffers all relevant data per context at the executor node there's no performance
        // benefit to keeping it iterable.
        if (value instanceof Iterable) {
            var iterator = ((Iterable<?>)value).iterator();
            var newList = new ArrayList<Object>();
            while (iterator.hasNext())
                newList.add(iterator.next());
            value = newList;
        }

        if (value instanceof ArrayList) {
            // For each array element, translate the type to remove any CQF dependencies
            // and ensure any type used supports Serializable to allow spark broadcasting.
            var a = (ArrayList<?>)value;
            var r = a.stream().map(element -> {
                // A CQL/CQF Tuple is really a name/value pair map. CQF adds a State object so
                // convert to a native Java Map and drop any supplementary state.
                if (element instanceof Tuple) {
                    // If the tuple value contains nested expressions like Tuples or Lists,
                    // process them recursively. Otherwise, its flat and use the value as-is.
                    var tuple = (Tuple)element;
                    return tuple.getElements().entrySet().stream().map(tupleElement -> {
                        if (tupleElement instanceof ArrayList || tupleElement instanceof Tuple)
                            return new AbstractMap.SimpleEntry<>(
                                    tupleElement.getKey(),
                                    (Object)convertResultValue(tupleElement.getValue()));
                         else
                            return tupleElement;
                    }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                } else {
                    // Return as-is with the assumption the value is expected.
                    // TODO: Check type against a pre-generated whitelist of expected types using model adapters.
                    return element;
                }
            });

            return new ArrayList<Object>(Collections.singletonList(r));
        }

        if (value == null)
            return List.of();

        // TODO: Check type against a pre-generated whitelist of expected types using model adapters.
        return List.of(value);
        // throw new RuntimeException("Unexpected result type '" + value.getClass().getSimpleName() + "' from CQF Engine");
    }

    private Map<String, DataProvider> buildDataProviders(ModelAdapterSet modelAdapterSet, Map<RetrieveDefinition, List<Object>> clinicalData) {
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