package gov.va.sparkcql;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.log.Log;
import gov.va.sparkcql.pipeline.compiler.CompilerFactory;
import gov.va.sparkcql.pipeline.evaluator.EvaluatorFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterFactory;
import gov.va.sparkcql.pipeline.preprocessor.PreprocessorFactory;
import gov.va.sparkcql.pipeline.retriever.RetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.types.QualifiedIdentifier;

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

    private enum Action { EVALUATE, EXTRACT, PLAN }
    private Action action;

    private enum Orientation { BY_CONTEXT, BY_EXPRESSION_DEF, BY_DATA_TYPE }
    private Orientation orientation;

    private enum Form { EXPANDED, COMPACTED }
    private Form form;

    public CqlPipelineBuilder() {
        additionalLibrarySources = new ArrayList<>();
        targetLibraries = new ArrayList<>();
        this.parameters = new HashMap<>();

        this.configuration = new EnvironmentConfiguration();
        defaultBindings(SparkFactory.class, true);
        defaultBindings(TableResolutionStrategyFactory.class, true);
        defaultBindings(RetrieverFactory.class, true);
        defaultBindings(CompilerFactory.class, true);
        defaultBindings(EvaluatorFactory.class, true);
        defaultBindings(PreprocessorFactory.class, false);
        defaultBindings(ModelAdapterFactory.class, false);
    }

    @SuppressWarnings("unchecked")
    private <I> void defaultBindings(Class<I> interfaceClass, boolean isExclusive) {
        // Determine if the client has explicitly set this binding.
        var clientBinding = this.configuration.readBinding(interfaceClass);
        if (clientBinding.isEmpty()) {
            // No explicit binding so implicitly load via ServiceLoader but only if it's
            // inclusive, meaning multiple implementations can co-exist.
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

    public EvaluateOrientationBuilder evaluate(QualifiedIdentifier libraryIdentifier) {
        this.targetLibraries.add(libraryIdentifier);
        this.action = Action.EVALUATE;
        return new EvaluateOrientationBuilder(this);
    }

    public EvaluateOrientationBuilder evaluate(List<QualifiedIdentifier> libraryIdentifiers) {
        this.targetLibraries.addAll(libraryIdentifiers);
        this.action = Action.EVALUATE;
        return new EvaluateOrientationBuilder(this);
    }

    public ExtractOrientationBuilder extractRequiredData(QualifiedIdentifier libraryIdentifier) {
        this.action = Action.EXTRACT;
        return new ExtractOrientationBuilder(this);
    }

    public ExtractOrientationBuilder extractRequiredData(List<QualifiedIdentifier> libraryIdentifiers) {
        this.action = Action.EXTRACT;
        return new ExtractOrientationBuilder(this);
    }

    public PlanRunBuilder plan(QualifiedIdentifier libraryIdentifier) {
        this.targetLibraries.add(libraryIdentifier);
        this.action = Action.PLAN;
        return new PlanRunBuilder(this);
    }

    public PlanRunBuilder plan(List<QualifiedIdentifier> libraryIdentifiers) {
        this.targetLibraries.addAll(libraryIdentifiers);
        this.action = Action.PLAN;
        return new PlanRunBuilder(this);
    }

    private void validate() {
        // TODO: Ensure all parameters are properly set w/o conflict
    }

    static public final class EvaluateOrientationBuilder {

        private final CqlPipelineBuilder parentBuilder;

        private EvaluateOrientationBuilder(CqlPipelineBuilder parentBuilder) {
            this.parentBuilder = parentBuilder;
        }

        public EvaluateFormBuilder byContext() {
            this.parentBuilder.orientation = Orientation.BY_CONTEXT;
            return new EvaluateFormBuilder(parentBuilder);
        }

        public EvaluateFormBuilder byExpressionDef() {
            this.parentBuilder.orientation = Orientation.BY_EXPRESSION_DEF;
            return new EvaluateFormBuilder(parentBuilder);
        }
    }

    static public final class EvaluateFormBuilder {

        private final CqlPipelineBuilder parentBuilder;

        private EvaluateFormBuilder(CqlPipelineBuilder parentBuilder) {
            this.parentBuilder = parentBuilder;
        }

        public EvaluateRunBuilder expanded() {
            this.parentBuilder.form = Form.EXPANDED;
            return new EvaluateRunBuilder(parentBuilder);
        }

        public EvaluateRunBuilder compacted() {
            this.parentBuilder.form = Form.COMPACTED;
            return new EvaluateRunBuilder(parentBuilder);
        }
    }

    static public final class EvaluateRunBuilder {

        private final CqlPipelineBuilder parentBuilder;

        private EvaluateRunBuilder(CqlPipelineBuilder parentBuilder) {
            this.parentBuilder = parentBuilder;
        }

        public Object run() {
            this.parentBuilder.validate();
            throw new UnsupportedOperationException();
        }
    }

    static public final class ExtractOrientationBuilder {

        private final CqlPipelineBuilder parentBuilder;

        private ExtractOrientationBuilder(CqlPipelineBuilder parentBuilder) {
            this.parentBuilder = parentBuilder;
        }

        public ExtractRunBuilder byContext() {
            this.parentBuilder.orientation = Orientation.BY_CONTEXT;
            return new ExtractRunBuilder(parentBuilder);
        }

        public ExtractRunBuilder byDataType() {
            this.parentBuilder.orientation = Orientation.BY_DATA_TYPE;
            return new ExtractRunBuilder(parentBuilder);
        }
    }

    static public final class ExtractRunBuilder {

        private final CqlPipelineBuilder parentBuilder;

        private ExtractRunBuilder(CqlPipelineBuilder parentBuilder) {
            this.parentBuilder = parentBuilder;
        }

        public Object run() {
            this.parentBuilder.validate();
            throw new UnsupportedOperationException();
        }
    }

    static public final class PlanRunBuilder {

        private final CqlPipelineBuilder parentBuilder;

        private PlanRunBuilder(CqlPipelineBuilder parentBuilder) {
            this.parentBuilder = parentBuilder;
        }

        public Plan run() {
            this.parentBuilder.validate();
            throw new UnsupportedOperationException();
        }
    }
}