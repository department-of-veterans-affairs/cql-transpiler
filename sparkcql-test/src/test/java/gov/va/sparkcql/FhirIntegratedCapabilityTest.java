package gov.va.sparkcql;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.pipeline.Pipeline;
import gov.va.sparkcql.pipeline.compiler.CompilerFactory;
import gov.va.sparkcql.pipeline.compiler.CqfCompilerFactory;
import gov.va.sparkcql.pipeline.evaluator.CqfEvaluatorFactory;
import gov.va.sparkcql.pipeline.evaluator.EvaluatorFactory;
import gov.va.sparkcql.pipeline.model.FhirModelAdapterFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterFactory;
import gov.va.sparkcql.pipeline.preprocessor.FhirSyntheticDataPreprocessorFactory;
import gov.va.sparkcql.pipeline.preprocessor.PreprocessorFactory;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceFileRepositoryFactory;
import gov.va.sparkcql.pipeline.repository.cql.CqlSourceRepositoryFactory;
import gov.va.sparkcql.pipeline.retriever.RetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.SparkIndexedDataRetrieverFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategyFactory;
import gov.va.sparkcql.runtime.LocalSparkFactory;
import gov.va.sparkcql.runtime.SparkFactory;
import org.junit.jupiter.api.Test;

public class FhirIntegratedCapabilityTest extends AbstractIntegrationTest {

    @Override
    protected Configuration configure() {
        return new EnvironmentConfiguration()
                .writeBinding(SparkFactory.class, LocalSparkFactory.class)
                .writeBinding(CqlSourceRepositoryFactory.class, CqlSourceFileRepositoryFactory.class)
                .writeSetting(CqlSourceFileRepositoryFactory.CQL_SOURCE_FILE_REPOSITORY_PATH, "./src/test/resources/fhir/cql")
                .writeBinding(CompilerFactory.class, CqfCompilerFactory.class)
                .writeBinding(TableResolutionStrategyFactory.class, TemplateResolutionStrategyFactory.class)
                .writeBinding(RetrieverFactory.class, SparkIndexedDataRetrieverFactory.class)
                .writeBinding(EvaluatorFactory.class, CqfEvaluatorFactory.class)
                .writeBinding(PreprocessorFactory.class, FhirSyntheticDataPreprocessorFactory.class)
                .writeSetting(TemplateResolutionStrategyFactory.TEMPLATE_RESOLUTION_STRATEGY, "fhir_${domain}")
                .writeBinding(ModelAdapterFactory.class, FhirModelAdapterFactory.class);
    }
//
//    @Test
//    public void should_prove_fhir_engine_conformity() {
//        var pipeline = new Pipeline(getConfiguration());
//        var results = pipeline.execute("FhirEngineConformity", "1.0");
//        showResults(results);
//    }
//
//    @Test
//    public void should_prove_quick_engine_conformity() {
//        var pipeline = new Pipeline(getConfiguration());
//        var results = pipeline.execute("QuickEngineConformity", "1.0");
//        showResults(results);
//    }

}