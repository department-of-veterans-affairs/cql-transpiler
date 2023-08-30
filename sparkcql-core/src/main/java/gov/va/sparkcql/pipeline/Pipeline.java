package gov.va.sparkcql.pipeline;

import java.util.*;
import java.util.stream.Collectors;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.domain.*;
import gov.va.sparkcql.pipeline.compiler.CompilerFactory;
import gov.va.sparkcql.pipeline.converger.ConvergerFactory;
import gov.va.sparkcql.pipeline.converger.DefaultConvergerFactory;
import gov.va.sparkcql.pipeline.evaluator.EvaluatorFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterFactory;
import gov.va.sparkcql.pipeline.optimizer.DefaultOptimizerFactory;
import gov.va.sparkcql.pipeline.optimizer.OptimizerFactory;
import gov.va.sparkcql.pipeline.preprocessor.PreprocessorFactory;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepositoryFactory;
import gov.va.sparkcql.pipeline.retriever.RetrieverFactory;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.SparkSession;
import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.pipeline.converger.Converger;
import gov.va.sparkcql.pipeline.compiler.Compiler;
import gov.va.sparkcql.pipeline.evaluator.Evaluator;
import gov.va.sparkcql.pipeline.model.ModelAdapterComposite;
import gov.va.sparkcql.pipeline.optimizer.Optimizer;
import gov.va.sparkcql.pipeline.preprocessor.Preprocessor;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;
import gov.va.sparkcql.pipeline.retriever.Retriever;
import scala.Serializable;
import scala.Tuple2;

public class Pipeline implements Serializable {

    private final Configuration configuration;

    private final SparkFactory sparkFactory;

    private final SparkSession spark;

    // Pipeline Components
    private final Compiler compiler;

    private final Optimizer optimizer;

    private final Retriever retriever;

    private final ModelAdapterComposite modelAdapterComposite;

    private final Converger converger;

    private Evaluator evaluator;

    private List<Preprocessor> preprocessors;

    private final CqlSourceRepository cqlSourceRepository;

    // Invocation
    private Map<String, Object> parameters;

    // Stage Output
    private Plan compiledPlanOutput;

    private Plan plannedOutput;

    private Map<Retrieval, JavaRDD<Object>> retrievalOutput;

    private JavaPairRDD<String, Map<Retrieval, List<Object>>> combinedOutput;

    private JavaPairRDD<String, EvaluatedContext> evaluationOutput;

    public Pipeline(Configuration configuration) {
        this.configuration = configuration;
        var injector = new Injector(configuration);
        this.sparkFactory = injector.getInstance(SparkFactory.class);
        this.spark = sparkFactory.create();
        this.cqlSourceRepository = injector.getInstance(CqlSourceRepositoryFactory.class).create(sparkFactory);
        this.compiler = injector.getInstance(CompilerFactory.class).create(cqlSourceRepository);
        this.optimizer = injector.getInstance(OptimizerFactory.class, DefaultOptimizerFactory.class).create();
        this.retriever = injector.getInstance(RetrieverFactory.class).create(sparkFactory);
        List<ModelAdapter> modelAdapters = injector.getInstances(ModelAdapterFactory.class)
                .stream().map(f -> f.create()).collect(Collectors.toList());
        this.modelAdapterComposite = new ModelAdapterComposite(modelAdapters);
        this.converger = injector.getInstance(ConvergerFactory.class, DefaultConvergerFactory.class).create();
        this.preprocessors = injector.getInstances(PreprocessorFactory.class)
                .stream().map(f -> f.create(sparkFactory)).collect(Collectors.toList());
    }

    public EvaluationResultSet execute(String libraryName, String version, Map<String, Object> parameters) {
        this.compiledPlanOutput = getCompiler().compile(List.of(new VersionedIdentifier().withId(libraryName).withVersion(version)));
        this.parameters = parameters;
        return runPipeline();
    }

    public EvaluationResultSet execute(String libraryName, String version) {
        return execute(libraryName, version, Map.of());
    }

    public EvaluationResultSet execute(String cqlSource, Map<String, Object> parameters) {
        compiledPlanOutput = getCompiler().compile(cqlSource);
        this.parameters = parameters;
        return runPipeline();
    }

    public EvaluationResultSet execute(String cqlSource) {
        return execute(cqlSource, Map.of());
    }

    public EvaluationResultSet execute(Plan plan, Map<String, Object> parameters) {
        this.compiledPlanOutput = plan;
        this.parameters = parameters;
        return runPipeline();
    }

    public EvaluationResultSet execute(Plan plan) {
        this.compiledPlanOutput = plan;
        return runPipeline();
    }

    public EvaluationResultSet runPipeline() {

        // Run all preprocessors before anything else.
        runPreprocessStage();

        // Produce an optimized execution plan using the compiled ELMs.
        plannedOutput = runOptimizerStage();

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

    private Plan runOptimizerStage() {
        return getOptimizer().optimize(compiledPlanOutput);
    }

    private Map<Retrieval, JavaRDD<Object>> runRetrievalStage() {
        return plannedOutput.getRetrieves().stream()
                .collect(Collectors.toMap(r -> r, r -> {
                    var y = getRetriever().retrieve(r, getModelAdapterComposite());
                    return getRetriever().retrieve(r, getModelAdapterComposite());
                }));
    }

    private JavaPairRDD<String, Map<Retrieval, List<Object>>> runCombinerStage() {
        return getConverger().combine(retrievalOutput, plannedOutput, getModelAdapterComposite());
    }

    private JavaRDD<EvaluatedContext> runEvaluatorStage() {
        var factory = new Injector(configuration).getInstance(EvaluatorFactory.class);
        this.evaluator = factory.create(plannedOutput, modelAdapterComposite, null);

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
        return getEvaluator().evaluate(row._1, row._2);
    }

    public Compiler getCompiler() {
        return this.compiler;
    }

    public ModelAdapterComposite getModelAdapterComposite() {
        return modelAdapterComposite;
    }

    public Optimizer getOptimizer() {
        return optimizer;
    }

    public CqlSourceRepository getCqlSourceRepository() {
        return cqlSourceRepository;
    }

    public Retriever getRetriever() {
        return retriever;
    }

    public Converger getConverger() {
        return converger;
    }

    public Evaluator getEvaluator() {
        return evaluator;
    }

    public List<Preprocessor> getPreprocessors() {
        return preprocessors;
    }
    
    public SparkSession getSpark() {
        return spark;
    }
}