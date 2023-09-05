package gov.va.sparkcql;

import gov.va.sparkcql.types.QualifiedIdentifier;
import org.junit.jupiter.api.Test;

public class FhirIntegratedCapabilityTest extends AbstractTest {

//        return new EnvironmentConfiguration()
//                .writeBinding(SparkFactory.class, LocalSparkFactory.class)
//                .writeBinding(CqlSourceRepositoryFactory.class, CqlSourceFileRepositoryFactory.class)
//                .writeSetting(CqlSourceFileRepositoryFactory.CQL_SOURCE_FILE_REPOSITORY_PATH, "./src/test/resources/fhir/cql")
//                .writeBinding(CompilerFactory.class, CqfCompilerFactory.class)
//                .writeBinding(TableResolutionStrategyFactory.class, TemplateResolutionStrategyFactory.class)
//                .writeBinding(RetrieverFactory.class, SparkIndexedDataRetrieverFactory.class)
//                .writeBinding(EvaluatorFactory.class, CqfEvaluatorFactory.class)
//                .writeBinding(PreprocessorFactory.class, FhirSyntheticDataPreprocessorFactory.class)
//                .writeSetting(TemplateResolutionStrategyFactory.TEMPLATE_RESOLUTION_STRATEGY, "fhir_${domain}")
//                .writeBinding(ModelAdapterFactory.class, FhirModelAdapterFactory.class);

    @Test
    public void should_prove_fhir_engine_conformity() {

        var r = new CqlPipelineBuilder()
                .withConfig(configuration)
                .evaluate(new QualifiedIdentifier().withId("FhirEngineConformity").withId("1.0"))
                .byContext()
                .run();

        r.collect().forEach(System.out::println);
    }
//
//    @Test
//    public void should_prove_quick_engine_conformity() {
//        var pipeline = new Pipeline(getConfiguration());
//        var results = pipeline.execute("QuickEngineConformity", "1.0");
//        showResults(results);
//    }

}