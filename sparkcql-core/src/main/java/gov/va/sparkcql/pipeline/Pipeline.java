package gov.va.sparkcql.pipeline;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.domain.RetrievalOperation;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

import com.google.inject.Inject;

import gov.va.sparkcql.configuration.SparkFactory;
import gov.va.sparkcql.pipeline.combiner.Combiner;
import gov.va.sparkcql.pipeline.compiler.Compiler;
import gov.va.sparkcql.pipeline.engine.Engine;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;
import gov.va.sparkcql.pipeline.planner.Planner;
import gov.va.sparkcql.pipeline.preprocessor.Preprocessor;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;
import gov.va.sparkcql.pipeline.retriever.Retriever;

public class Pipeline {

    private final Set<Component> components;
    
    private SparkSession spark;

    @Inject
    public Pipeline(Set<Component> components, SparkFactory sparkFactory) {
        this.components = components;
        this.spark = sparkFactory.create();
    }

    public Map<String, Dataset<Row>> execute(String libraryName, String version, Map<String, Object> parameters) {
        var compilation = getCompiler().compile(List.of(new VersionedIdentifier().withId(libraryName).withVersion(version)));
        return executePipeline(compilation, parameters);
    }

    public Map<String, Dataset<Row>> execute(String libraryName, String version) {
        return execute(libraryName, version, Map.of());
    }

    public Map<String, Dataset<Row>> execute(String cqlSource, Map<String, Object> parameters) {
        var compilation = getCompiler().compile(cqlSource);
        return executePipeline(compilation, parameters);
    }

    public Map<String, Dataset<Row>> execute(String cqlSource) {
        return execute(cqlSource, Map.of());
    }
    
    public Map<String, Dataset<Row>> execute(LibraryCollection libraryCollection) {
        return executePipeline(libraryCollection, null);
    }

    public Map<String, Dataset<Row>> execute(LibraryCollection libraryCollection, Map<String, Object> parameters) {
        return executePipeline(libraryCollection, parameters);
    }

    public Map<String, Dataset<Row>> executePipeline(LibraryCollection libraryCollection, Map<String, Object> parameters) {

        // Execute any preprocessors first.
        getPreprocessors().forEach(Preprocessor::apply);

        // Produce an optimized execution plan using the compiled ELMs.
        var planned = getPlanner().plan(libraryCollection);
        
        // Acquire data for every retrieve operation as a series of datasets with
        // links back to retrieve definition which required it.
        var retrieved = batchRetrieve(planned);

        // Group each dataset by the context and collect its interior clinical data as a
        // nested list so there's one outer row per member. Add a hash of the retrieve operation
        // so we can still look it up later.
        var combined = getCombiner().combine(retrieved, planned, getModelAdapterResolver());
        combined.collect().forEach(System.out::println);

        // Output results to client

        return null;
    }

    private Map<RetrievalOperation, JavaRDD<Object>> batchRetrieve(Plan plan) {
        return plan.getRetrievalOperations().stream()
                .collect(Collectors.toMap(r -> r, r -> {
                    return getRetriever().retrieve(r.getRetrieve(), getModelAdapterResolver());
                }));
    }

    private void applyPreprocessors() {
        getPreprocessors().forEach(Preprocessor::apply);
    }

    @SuppressWarnings("unchecked")
    private <T extends Component> T findComponent(Class<? extends Component> componentClass) {
        var found = (Optional<T>)components.stream()
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
        return (List<T>)components.stream()
            .filter(c -> componentClass.isAssignableFrom(c.getClass()))
            .toList();
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

    public Engine getEngine() {
        return findComponent(Engine.class);
    }

    public List<Preprocessor> getPreprocessors() {
        return findComponents(Preprocessor.class);
    }
    
    public SparkSession getSpark() {
        return spark;
    }
}
