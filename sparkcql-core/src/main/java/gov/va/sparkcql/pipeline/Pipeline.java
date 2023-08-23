package gov.va.sparkcql.pipeline;

import java.util.List;

import com.google.inject.Inject;

import gov.va.sparkcql.pipeline.compiler.Compiler;
import gov.va.sparkcql.pipeline.engine.Engine;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapter;
import gov.va.sparkcql.pipeline.modeladapter.ModelAdapterResolver;
import gov.va.sparkcql.pipeline.planner.Planner;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepository;
import gov.va.sparkcql.pipeline.retriever.Retriever;

public class Pipeline {

    private List<Component> components;
    
    private Compiler compiler;

    private ModelAdapterResolver modelAdapterResolver;

    private Planner planner;

    private CqlSourceRepository cqlSourceRepository;

    private Retriever retriever;

    private Engine engine;

    @Inject
    public Pipeline(Compiler compiler, ModelAdapterResolver modelAdapterResolver, Planner planner,
            CqlSourceRepository cqlSourceRepository, Retriever retriever, Engine engine) {
        this.compiler = compiler;
        this.modelAdapterResolver = modelAdapterResolver;
        this.planner = planner;
        this.cqlSourceRepository = cqlSourceRepository;
        this.retriever = retriever;
        this.engine = engine;
        this.components = List.of(
                compiler, modelAdapterResolver, planner, 
                cqlSourceRepository, retriever, engine);
    }

    public Compiler getCompiler() {
        return compiler;
    }

    public ModelAdapterResolver getModelAdapterResolver() {
        return modelAdapterResolver;
    }

    public Planner getPlanner() {
        return planner;
    }

    public CqlSourceRepository getCqlSourceRepository() {
        return cqlSourceRepository;
    }

    public Retriever getRetriever() {
        return retriever;
    }

    public Engine getEngine() {
        return engine;
    }
}
