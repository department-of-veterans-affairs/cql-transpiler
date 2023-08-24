package gov.va.sparkcql.pipeline;

import java.util.*;
import java.util.stream.Collectors;

import gov.va.sparkcql.domain.EvaluationResult;
import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.domain.Retrieval;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
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

    private final Set<Stage> stages;

    private Map<String, Object> parameters;

    private LibraryCollection compilationOutput;

    private Plan plannedOutput;

    private Map<Retrieval, JavaRDD<Object>> retrievalOutput;

    private JavaPairRDD<String, Map<Retrieval, List<Object>>> combinedOutput;

    private JavaPairRDD<String, EvaluationResult> evaluationOutput;

    @Inject
    public Pipeline(Set<Stage> stages, SparkFactory sparkFactory) {
        this.stages = stages;
        this.spark = sparkFactory.create();
    }

    public Map<String, Dataset<Row>> execute(String libraryName, String version, Map<String, Object> parameters) {
        this.compilationOutput = getCompiler().compile(List.of(new VersionedIdentifier().withId(libraryName).withVersion(version)));
        this.parameters = parameters;
        return runPipeline();
    }

    public Map<String, Dataset<Row>> execute(String libraryName, String version) {
        return execute(libraryName, version, Map.of());
    }

    public Map<String, Dataset<Row>> execute(String cqlSource, Map<String, Object> parameters) {
        compilationOutput = getCompiler().compile(cqlSource);
        this.parameters = parameters;
        return runPipeline();
    }

    public Map<String, Dataset<Row>> execute(String cqlSource) {
        return execute(cqlSource, Map.of());
    }

    public Map<String, Dataset<Row>> execute(LibraryCollection libraryCollection, Map<String, Object> parameters) {
        this.compilationOutput = libraryCollection;
        this.parameters = parameters;
        return runPipeline();
    }

    public Map<String, Dataset<Row>> execute(LibraryCollection libraryCollection) {
        this.compilationOutput = libraryCollection;
        return runPipeline();
    }

    public Map<String, Dataset<Row>> runPipeline() {

        // Run all preprocessors before anything else.
        runPreprocessStage();

        // Produce an optimized execution plan using the compiled ELMs.
        plannedOutput = runPlanningStage();

        // Acquire data for every retrieve operation as a series of datasets with
        // links back to retrieve definition which required it.
        retrievalOutput = runRetrievalStage();

        // Group each dataset by the context and collect its interior clinical data as a
        // nested list so there's one outer row per member. Add a hash of the retrieve operation
        // so we can still look it up later.
        combinedOutput = runCombinerStage();
        combinedOutput.collect().forEach(System.out::println);

        // Calculate each context across all measures via the provided engine
        var evaluationOutput = runEvaluatorStage();

        // Output results to client

        return null;
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

    private JavaRDD<EvaluationResult> runEvaluatorStage() {
        return combinedOutput.mapPartitions((FlatMapFunction<Iterator<Tuple2<String, Map<Retrieval, List<Object>>>>, EvaluationResult>) row -> {

            // NOTE: Everything within mapPartitions is running on the executor nodes.
            // Any one-time initialization per partition (aka across several rows) should
            // be performed here rather than within Iterator<EvaluationResult> below.

            return new Iterator<EvaluationResult>() {

                @Override
                public boolean hasNext() {
                    return row.hasNext();
                }

                @Override
                public EvaluationResult next() {
                    // Iterate to the next context element for those defined within the partition.
                    var nextRow = row.next();
                    return new EvaluationResult();
                }
            };
        }, false);
    }

    @SuppressWarnings("unchecked")
    private <T extends Stage> T findComponent(Class<? extends Stage> componentClass) {
        var found = (Optional<T>) stages.stream()
            .filter(c -> componentClass.isAssignableFrom(c.getClass()))
            .findFirst();

        if (found.isEmpty()) {
            throw new RuntimeException("Unable to locate pipeline component " + componentClass.getSimpleName());
        } else {
            return found.get();
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends Stage> List<T> findComponents(Class<? extends Stage> componentClass) {
        return (List<T>) stages.stream()
            .filter(c -> componentClass.isAssignableFrom(c.getClass()))
            .collect(Collectors.toList());
    }

    public Set<Stage> getComponents() {
        return stages;
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

    public Evaluator getEngine() {
        return findComponent(Evaluator.class);
    }

    public List<Preprocessor> getPreprocessors() {
        return findComponents(Preprocessor.class);
    }
    
    public SparkSession getSpark() {
        return spark;
    }
}