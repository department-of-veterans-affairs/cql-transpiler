package gov.va.sparkcql.pipeline;

import java.util.*;
import java.util.stream.Collectors;

import gov.va.sparkcql.domain.*;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.SparkSession;
import org.hl7.elm.r1.VersionedIdentifier;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.pipeline.combiner.Combiner;
import gov.va.sparkcql.pipeline.compiler.Compiler;
import gov.va.sparkcql.pipeline.evaluator.Evaluator;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;
import gov.va.sparkcql.pipeline.planner.Planner;
import gov.va.sparkcql.pipeline.preprocessor.Preprocessor;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;
import gov.va.sparkcql.pipeline.retriever.Retriever;
import scala.Serializable;
import scala.Tuple2;

public class Pipeline implements Serializable {

    private final SparkSession spark;

    private final Set<Component> components;

    private Map<String, Object> parameters;

    private LibraryCollection compilationOutput;

    private Plan plannedOutput;

    private Map<Retrieval, JavaRDD<Object>> retrievalOutput;

    private JavaPairRDD<String, Map<Retrieval, List<Object>>> combinedOutput;

    private JavaPairRDD<String, EvaluatedContext> evaluationOutput;

    @Inject
    public Pipeline(Set<Component> components, SparkFactory sparkFactory) {
        this.components = components;
        this.spark = sparkFactory.create();
    }

    public EvaluationResultSet execute(String libraryName, String version, Map<String, Object> parameters) {
        this.compilationOutput = getCompiler().compile(List.of(new VersionedIdentifier().withId(libraryName).withVersion(version)));
        this.parameters = parameters;
        return runPipeline();
    }

    public EvaluationResultSet execute(String libraryName, String version) {
        return execute(libraryName, version, Map.of());
    }

    public EvaluationResultSet execute(String cqlSource, Map<String, Object> parameters) {
        compilationOutput = getCompiler().compile(cqlSource);
        this.parameters = parameters;
        return runPipeline();
    }

    public EvaluationResultSet execute(String cqlSource) {
        return execute(cqlSource, Map.of());
    }

    public EvaluationResultSet execute(LibraryCollection libraryCollection, Map<String, Object> parameters) {
        this.compilationOutput = libraryCollection;
        this.parameters = parameters;
        return runPipeline();
    }

    public EvaluationResultSet execute(LibraryCollection libraryCollection) {
        this.compilationOutput = libraryCollection;
        return runPipeline();
    }

    public EvaluationResultSet runPipeline() {

        // Run all preprocessors before anything else.
        runPreprocessStage();

        // Produce an optimized execution plan using the compiled ELMs.
        plannedOutput = runPlanningStage();

        // Acquire data for every retrieve operation as a series of datasets with
        // links back to retrieve definition which required it.
        retrievalOutput = runRetrievalStage();

        // Group each dataset by the context element and collect its interior clinical data as a
        // nested list so there's one outer row per member. Add a hash of the retrieve operation
        // so we can still look it up later.
        combinedOutput = runCombinerStage();

        // Calculate each context across all measures via the provided engine
        var evaluationOutput = runEvaluatorStage();

        // Output results to client
        return new EvaluationResultSet(evaluationOutput);
    }

    private void runPreprocessStage() {
        getPreprocessors().forEach(Preprocessor::apply);
    }

    private Plan runPlanningStage() {
        return getPlanner().plan(compilationOutput);
    }

    private Map<Retrieval, JavaRDD<Object>> runRetrievalStage() {
        return plannedOutput.getRetrieves().stream()
                .collect(Collectors.toMap(r -> r, r -> {
                    var y = getRetriever().retrieve(r, getModelAdapterResolver());
                    return getRetriever().retrieve(r, getModelAdapterResolver());
                }));
    }

    private JavaPairRDD<String, Map<Retrieval, List<Object>>> runCombinerStage() {
        return getCombiner().combine(retrievalOutput, plannedOutput, getModelAdapterResolver());
    }

    private JavaRDD<EvaluatedContext> runEvaluatorStage() {
        return combinedOutput.mapPartitions((FlatMapFunction<Iterator<Tuple2<String, Map<Retrieval, List<Object>>>>, EvaluatedContext>) row -> {

            // NOTE: Everything within mapPartitions is running on the executor nodes.
            // Any one-time initialization per partition (aka across several rows) should
            // be performed here rather than within Iterator<EvaluatedContext> below.

            return new Iterator<EvaluatedContext>() {

                @Override
                public boolean hasNext() {
                    return row.hasNext();
                }

                @Override
                public EvaluatedContext next() {
                    // Iterate to the next context element for those defined within the partition.
                    var nextRow = row.next();
                    return runEvaluatorContextElement(nextRow);
                }
            };
        }, true);
    }

    EvaluatedContext runEvaluatorContextElement(Tuple2<String, Map<Retrieval, List<Object>>> row) {
        return getEvaluator().evaluate(row._1, compilationOutput, row._2, null);
    }

    @SuppressWarnings("unchecked")
    private <T extends Component> T findComponent(Class<? extends Component> componentClass) {
        var found = (Optional<T>) components.stream()
            .filter(c -> componentClass.isAssignableFrom(c.getClass()))
            .findFirst();

        if (found.isEmpty()) {
            throw new RuntimeException("Unable to locate pipeline component " + componentClass.getSimpleName());
        } else {
            return found.get();
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Component> List<T> findComponents(Class<? extends Component> componentClass) {
        return (List<T>) components.stream()
            .filter(c -> componentClass.isAssignableFrom(c.getClass()))
            .collect(Collectors.toList());
    }

    public Set<Component> getComponents() {
        return components;
    }

    public Compiler getCompiler() {
        return findComponent(Compiler.class);
    }

    public ModelAdapterResolver getModelAdapterResolver() {
        return findComponent(ModelAdapterResolver.class);
    }

    public Planner getPlanner() {
        return findComponent(Planner.class);
    }

    public CqlSourceRepository getCqlSourceRepository() {
        return findComponent(CqlSourceRepository.class);
    }

    public Retriever getRetriever() {
        return findComponent(Retriever.class);
    }

    public Combiner getCombiner() {
        return findComponent(Combiner.class);
    }

    public Evaluator getEvaluator() {
        return findComponent(Evaluator.class);
    }

    public List<Preprocessor> getPreprocessors() {
        return findComponents(Preprocessor.class);
    }
    
    public SparkSession getSpark() {
        return spark;
    }
}