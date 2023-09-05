package gov.va.sparkcql;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.ExpressionReference;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.log.Log;
import gov.va.sparkcql.pipeline.Pipeline;
import gov.va.sparkcql.pipeline.compiler.CompilerFactory;
import gov.va.sparkcql.pipeline.converger.Converger;
import gov.va.sparkcql.pipeline.evaluator.EvaluatorFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterFactory;
import gov.va.sparkcql.pipeline.optimizer.Optimizer;
import gov.va.sparkcql.pipeline.preprocessor.PreprocessorFactory;
import gov.va.sparkcql.pipeline.retriever.RetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.types.QualifiedIdentifier;
import org.apache.spark.api.java.JavaRDD;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class CqlPipelineBuilder {

    private List<String> additionalLibrarySources;
    private List<QualifiedIdentifier> targetLibraries;
    private Configuration configuration;
    private Plan explicitPlan;
    private Map<String, Object> parameters;

    public CqlPipelineBuilder() {
        additionalLibrarySources = new ArrayList<>();
        targetLibraries = new ArrayList<>();
        this.parameters = new HashMap<>();

        this.configuration = new EnvironmentConfiguration();
        defaultBindings(SparkFactory.class, true);
        defaultBindings(PreprocessorFactory.class, false);
        defaultBindings(CompilerFactory.class, true);
        defaultBindings(Optimizer.class, true);
        defaultBindings(RetrieverFactory.class, true);
        defaultBindings(TableResolutionStrategyFactory.class, true);
        defaultBindings(ModelAdapterFactory.class, false);
        defaultBindings(Converger.class, true);
        defaultBindings(EvaluatorFactory.class, true);
    }

    @SuppressWarnings("unchecked")
    private <I> void defaultBindings(Class<I> interfaceClass, boolean isExclusive) {
        // If the client hasn't already set this binding explicitly.
        if (!this.configuration.hasBinding(interfaceClass)) {
            // Implicitly load via ServiceLoader but only if it's inclusive,
            // meaning multiple implementations can co-exist.
            var x = ServiceLoader.load(interfaceClass).stream().collect(Collectors.toList());
            var serviceTypes = ServiceLoader.load(interfaceClass).stream()
                    .map(p -> (Class<? extends I>)p.get().getClass())
                    .collect(Collectors.toList());

            if (isExclusive && serviceTypes.size() > 1) {
                Log.warn("Multiple implementations exist for " + interfaceClass.getCanonicalName() + " but only one can be selected. Explicitly bind one of the following:");
                serviceTypes.forEach(t -> Log.warn("\t" + t.getCanonicalName()));
                throw new RuntimeException("Ambiguous implementations for '" + interfaceClass.getCanonicalName() + "'.");
            } else {
                this.configuration.writeBinding(interfaceClass, new ArrayList<>(serviceTypes));
            }
        } else {
            Log.info(interfaceClass.getSimpleName() + " already bound.");
        }
    }

    public CqlPipelineBuilder withSetting(String key, String value) {
        configuration.writeSetting(key, value);
        return this;
    }

    public <I> CqlPipelineBuilder withBinding(Class<I> interfaceClass, Class<I> implementationClass) {
        configuration.writeBinding(interfaceClass, implementationClass);
        return this;
    }

    public CqlPipelineBuilder withCql(String libraryName, String version) {

        return this;
    }

    public CqlPipelineBuilder withCql(String cqlSource) {
        this.additionalLibrarySources.add(cqlSource);
        return this;
    }

    public CqlPipelineBuilder withCql(List<String> cqlSources) {
        this.additionalLibrarySources.addAll(cqlSources);
        return this;
    }

    public CqlPipelineBuilder withPlan(Plan plan) {
        this.explicitPlan = plan;
        return this;
    }

    public CqlPipelineBuilder withParameter(String name, Date value) {
        this.parameters.put(name, value);
        return this;
    }

    public CqlPipelineBuilder withParameter(String name, LocalDateTime value) {
        this.parameters.put(name, value);
        return this;
    }

    public CqlPipelineBuilder withParameter(String name, Period value) {
        this.parameters.put(name, value);
        return this;
    }

    public EvaluateBuilder evaluate(QualifiedIdentifier libraryIdentifier) {
        this.targetLibraries.add(libraryIdentifier);
        return new EvaluateBuilder(this);
    }

    public EvaluateBuilder evaluate(List<QualifiedIdentifier> libraryIdentifiers) {
        this.targetLibraries.addAll(libraryIdentifiers);
        return new EvaluateBuilder(this);
    }

    public RetrieveBuilder retrieve(QualifiedIdentifier libraryIdentifier) {
        return new RetrieveBuilder(this);
    }

    public RetrieveBuilder retrieve(List<QualifiedIdentifier> libraryIdentifiers) {
        return new RetrieveBuilder(this);
    }

    public PlanRunBuilder plan(QualifiedIdentifier libraryIdentifier) {
        this.targetLibraries.add(libraryIdentifier);
        return new PlanRunBuilder(this);
    }

    public PlanRunBuilder plan(List<QualifiedIdentifier> libraryIdentifiers) {
        this.targetLibraries.addAll(libraryIdentifiers);
        return new PlanRunBuilder(this);
    }

    private void validate() {
        // TODO: Ensure all parameters are properly set w/o conflict
    }

    static public final class EvaluateBuilder {

        private final CqlPipelineBuilder parent;

        private EvaluateBuilder(CqlPipelineBuilder parent) {
            this.parent = parent;
        }

        public EvaluateByContextRunner byContext() {
            return new EvaluateByContextRunner(this);
        }

        public EvaluateByExprDefRunner byExpressionDef() {
            return new EvaluateByExprDefRunner(this);
        }

        static public final class EvaluateByContextRunner {

            private final EvaluateBuilder parent;

            private EvaluateByContextRunner(EvaluateBuilder parent) {
                this.parent = parent;
            }

            public JavaRDD<EvaluatedContext> run() {
                var root = this.parent.parent;
                root.validate();
                var pipeline = new Pipeline(root.configuration);
                var plan = pipeline.plan(root.targetLibraries, root.additionalLibrarySources);
                var results = pipeline.execute(plan);
                return results.splitByContext();
            }
        }

        static public final class EvaluateByExprDefRunner {

            private final EvaluateBuilder parent;

            private EvaluateByExprDefRunner(EvaluateBuilder parent) {
                this.parent = parent;
            }

            public Map<ExpressionReference, JavaRDD<Object>> run() {
                var root = this.parent.parent;
                root.validate();
                var pipeline = new Pipeline(root.configuration);
                var plan = pipeline.plan(root.targetLibraries, root.additionalLibrarySources);
                var results = pipeline.execute(plan);
                return results.splitByExprDef();
            }
        }
    }

    static public final class RetrieveBuilder {

        private final CqlPipelineBuilder parent;

        private RetrieveBuilder(CqlPipelineBuilder parent) {
            this.parent = parent;
        }

        public RetrieveByContextRunner byContext() {
            return new RetrieveByContextRunner(this);
        }

        public RetrieveByDataTypeRunner byDataType() {
            return new RetrieveByDataTypeRunner(this);
        }

        static public final class RetrieveByContextRunner {

            private final RetrieveBuilder parent;

            private RetrieveByContextRunner(RetrieveBuilder parent) {
                this.parent = parent;
            }

            public Object run() {
                var root = this.parent.parent;
                root.validate();
                throw new UnsupportedOperationException();
            }
        }

        static public final class RetrieveByDataTypeRunner {

            private final RetrieveBuilder parent;

            private RetrieveByDataTypeRunner(RetrieveBuilder parent) {
                this.parent = parent;
            }

            public Object run() {
                var root = this.parent.parent;
                root.validate();
                throw new UnsupportedOperationException();
            }
        }
    }

    static public final class PlanRunBuilder {

        private final CqlPipelineBuilder parent;

        private PlanRunBuilder(CqlPipelineBuilder parent) {
            this.parent = parent;
        }

        public Plan run() {
            this.parent.validate();
            var pipeline = new Pipeline(this.parent.configuration);
            return pipeline.plan(this.parent.targetLibraries, this.parent.additionalLibrarySources);
        }
    }
}