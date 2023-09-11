package gov.va.sparkcql;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.ExpressionReference;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.log.Log;
import gov.va.sparkcql.pipeline.Pipeline;
import gov.va.sparkcql.pipeline.compiler.CompilerFactory;
import gov.va.sparkcql.pipeline.converger.ConvergerFactory;
import gov.va.sparkcql.pipeline.evaluator.EvaluatorFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterFactory;
import gov.va.sparkcql.pipeline.optimizer.OptimizerFactory;
import gov.va.sparkcql.pipeline.preprocessor.PreprocessorFactory;
import gov.va.sparkcql.pipeline.retriever.RetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;
import gov.va.sparkcql.pipeline.runtime.SparkFactory;
import gov.va.sparkcql.types.QualifiedIdentifier;
import org.apache.spark.api.java.JavaRDD;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class CqlSession {

    private CqlSessionBuilder builder;

    private CqlSession(CqlSessionBuilder builder) {
        this.builder = builder;
    }

    public static CqlSessionBuilder build() {
        return new CqlSessionBuilder();
    }

    public static class CqlSessionBuilder {

        private Configuration configuration;

        private CqlSessionBuilder() {
            this.configuration = new EnvironmentConfiguration();
        }

        private Configuration getEffectiveConfiguration() {
            // Apply default configuration for those settings which were not set explicitly.
            setDefaultBindings(SparkFactory.class, true);
            setDefaultBindings(PreprocessorFactory.class, false);
            setDefaultBindings(CompilerFactory.class, true);
            setDefaultBindings(OptimizerFactory.class, true);
            setDefaultBindings(RetrieverFactory.class, true);
            setDefaultBindings(TableResolutionStrategyFactory.class, true);
            setDefaultBindings(ModelAdapterFactory.class, false);
            setDefaultBindings(ConvergerFactory.class, true);
            setDefaultBindings(EvaluatorFactory.class, true);

            return this.configuration;
        }

        @SuppressWarnings("unchecked")
        private <I> void setDefaultBindings(Class<I> interfaceClass, boolean isExclusive) {
            // If the client hasn't already set this binding explicitly.
            if (!this.configuration.hasBinding(interfaceClass)) {
                // Implicitly load via ServiceLoader but only if it's inclusive,
                // meaning multiple implementations can co-exist.
                var x = ServiceLoader.load(interfaceClass).stream().collect(Collectors.toList());
                var serviceTypes = ServiceLoader.load(interfaceClass).stream()
                        .map(p -> (Class<? extends I>) p.get().getClass())
                        .collect(Collectors.toList());

                if (isExclusive && serviceTypes.size() > 1) {
                    Log.warn("Multiple implementations exist for " + interfaceClass.getCanonicalName() + " but only one can be selected. Explicitly bind one of the following:");
                    serviceTypes.forEach(t -> Log.warn("\t" + t.getCanonicalName()));
                    throw new RuntimeException("Ambiguous implementations for '" + interfaceClass.getCanonicalName() + "'.");
                } else {
                    this.configuration.writeBinding(interfaceClass, new ArrayList<>(serviceTypes));
                }
            }
        }

        public CqlSessionBuilder withSetting(String key, String value) {
            configuration.writeSetting(key, value);
            return this;
        }

        public CqlSessionBuilder withConfiguration(Configuration configuration) {
            // Apply the new configuration to current state.
            configuration.readAllSettings().forEach((key, value) -> {
                this.configuration.writeSetting(key, value);
            });
            return this;
        }

        public <I> CqlSessionBuilder withBinding(Class<I> interfaceClass, Class<? extends I> implementationClass) {
            configuration.writeBinding(interfaceClass, implementationClass);
            return this;
        }

        public CqlSession create() {
            validate();
            return new CqlSession(this);
        }

        private void validate() {
            // TODO: Ensure all parameters are properly set w/o conflict
        }
    }
}