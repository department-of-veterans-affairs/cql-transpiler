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
import gov.va.sparkcql.pipeline.model.ModelAdapterFactory;
import gov.va.sparkcql.pipeline.optimizer.DefaultOptimizerFactory;
import gov.va.sparkcql.pipeline.optimizer.OptimizerFactory;
import gov.va.sparkcql.pipeline.preprocessor.PreprocessorFactory;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepositoryFactory;
import gov.va.sparkcql.pipeline.retriever.RetrieverFactory;
import gov.va.sparkcql.types.QualifiedIdentifier;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.SparkSession;

import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.converger.Converger;
import gov.va.sparkcql.pipeline.compiler.Compiler;
import gov.va.sparkcql.pipeline.evaluator.Evaluator;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
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
    private final List<Preprocessor> preprocessors;
    private final Compiler compiler;
    private final Optimizer optimizer;
    private final Retriever retriever;
    private final ModelAdapterSet modelAdapterSet;
    private final Converger converger;
    private final EvaluatorFactory evaluatorFactory;
    private final CqlSourceRepository cqlSourceRepository;
    private Object terminologyRepository;

    // Invocation
    private Map<String, Object> parameters;
    private Plan plan;

    // Stage Output
    private Map<Retrieval, JavaRDD<Object>> retrievalOutput;
    private JavaPairRDD<String, Map<Retrieval, List<Object>>> combinedOutput;
    private JavaPairRDD<String, EvaluatedContext> evaluationOutput;

    public Pipeline(Configuration configuration) {
        this.configuration = configuration;
        var injector = new Injector(configuration);

        // Construction Spark
        this.sparkFactory = injector.getInstance(SparkFactory.class);
        this.spark = sparkFactory.create(configuration);

        // Construct Model Adapters used to adapt model semantics to runtime.
        var modelAdapters = injector.getInstances(ModelAdapterFactory.class)
                .stream().map(f -> f.create(configuration)).collect(Collectors.toList());
        this.modelAdapterSet = new ModelAdapterSet(modelAdapters);

        // Construct preprocessors which initialize the pipeline ahead of other stages.
        if (configuration.hasBinding(PreprocessorFactory.class)) {
            this.preprocessors = injector.getInstances(PreprocessorFactory.class)
                    .stream().map(f -> f.create(configuration, sparkFactory, modelAdapterSet)).collect(Collectors.toList());
        } else {
            this.preprocessors = List.of();
        }

        // Construct Compiler and CQL Source Repository used to fetch CQL scripts by the Compiler.
        if (configuration.hasBinding(CqlSourceRepositoryFactory.class)) {
            this.cqlSourceRepository = injector.getInstance(CqlSourceRepositoryFactory.class).create(configuration, sparkFactory);
        } else {
            this.cqlSourceRepository = null;
        }
        this.compiler = injector.getInstance(CompilerFactory.class).create(configuration, cqlSourceRepository);

        // Construct Optimizer used to optimize the retrievals to satisfy data requirements.
        this.optimizer = injector.getInstance(OptimizerFactory.class, DefaultOptimizerFactory.class).create(configuration);

        // Construct Converger used to bring join all retrieved feeds into a single "bundle".
        this.converger = injector.getInstance(ConvergerFactory.class, DefaultConvergerFactory.class).create(configuration);

        // Construct Retrievers which provide clinical data to the engine.
        this.retriever = injector.getInstance(RetrieverFactory.class).create(configuration, sparkFactory);

        // Construct Evaluator used as the CQL engine. Note that the Evaluator constructor
        // requires a Plan which is created late during pipeline execution.
        this.evaluatorFactory = injector.getInstance(EvaluatorFactory.class);

        // Construct Terminology Repository which provide terminology data to the engine.
        // TODO
    }

    public Plan plan(List<QualifiedIdentifier> targetIdentifiedLibraries, List<String> targetAnonymousLibraries) {

        // Run all preprocessors before anything else.
        runPreprocessStage();

        // Compile all target CQL sources (identified or anonymous) into an ELM list.
        var anonymousLibraries = targetAnonymousLibraries.toArray(new String[0]);
        var unoptimizedPlan = getCompiler().compile(targetIdentifiedLibraries, anonymousLibraries);

        // Produce an optimized execution plan using the compiled ELMs.
        return getOptimizer().optimize(unoptimizedPlan);
    }

    public EvaluationOutput execute(Plan plan) {
        return execute(plan, null);
    }

    public EvaluationOutput execute(Plan plan, Map<String, Object> parameters) {

        // Run all preprocessors before anything else.
        runPreprocessStage();

        // Optimize the specified plan if not already.
        if (!plan.isOptimized()) {
            this.plan = getOptimizer().optimize(plan);
        }

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
        return new EvaluationOutput(evaluationOutput);
    }

    private void runPreprocessStage() {
        getPreprocessors().forEach(Preprocessor::apply);
    }

    private Map<Retrieval, JavaRDD<Object>> runRetrievalStage() {
        return plan.getRetrieves().stream()
                .collect(Collectors.toMap(r -> r, r -> {
                    return getRetriever().retrieve(r, getModelAdapterCollection());
                }));
    }

    private JavaPairRDD<String, Map<Retrieval, List<Object>>> runCombinerStage() {
        // No retrievals found is an indicator of a CQL which only contain literal
        // definitions, mostly for testing scenarios. While there's little real-world
        // value for these kinds of definitions, we shouldn't error and should at least
        // return a zero-record dataset.
        if (retrievalOutput.isEmpty()) {
            var sc = JavaSparkContext.fromSparkContext(spark.sparkContext());
            List<Tuple2<String, Map<Retrieval, List<Object>>>> emptyList = List.of();
            return sc.parallelizePairs(emptyList);
        }

        return getConverger().converge(retrievalOutput, plan, getModelAdapterCollection());
    }

    private JavaRDD<EvaluatedContext> runEvaluatorStage() {

        return combinedOutput.mapPartitions((FlatMapFunction<Iterator<Tuple2<String, Map<Retrieval, List<Object>>>>, EvaluatedContext>) row -> {

            // NOTE: Everything within mapPartitions is running on the executor nodes.
            // Any one-time initialization per partition (aka across several rows) should
            // be performed here rather than within Iterator<EvaluatedContext> below.

            // Construct the Evaluator used as the CQL engine. Since we cannot control
            // whether the engine or its dependencies implements Serializable, we cannot
            // instantiate this call on the driver. It must be created on each executor.
            var evaluator = evaluatorFactory.create(
                    this.configuration,
                    plan,
                    modelAdapterSet,
                    terminologyRepository);

            return new Iterator<EvaluatedContext>() {

                @Override
                public boolean hasNext() {
                    return row.hasNext();
                }

                @Override
                public EvaluatedContext next() {
                    // Iterate to the next context element for those defined within the partition.
                    var nextRow = row.next();
                    return runEvaluatorContextElement(evaluator, nextRow);
                }
            };
        }, true);
    }

    EvaluatedContext runEvaluatorContextElement(Evaluator evaluator, Tuple2<String, Map<Retrieval, List<Object>>> row) {
        return evaluator.evaluate(row._1, row._2);
    }

    public Compiler getCompiler() {
        return this.compiler;
    }

    public ModelAdapterSet getModelAdapterCollection() {
        return modelAdapterSet;
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

    public EvaluatorFactory getEvaluatorFactory() {
        return evaluatorFactory;
    }

    public List<Preprocessor> getPreprocessors() {
        return preprocessors;
    }
    
    public SparkSession getSpark() {
        return spark;
    }
}