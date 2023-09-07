package gov.va.sparkcql;

import gov.va.sparkcql.pipeline.compiler.CompilerFactory;
import gov.va.sparkcql.pipeline.compiler.CqfCompilerFactory;
import gov.va.sparkcql.pipeline.evaluator.CqfEvaluatorFactory;
import gov.va.sparkcql.pipeline.evaluator.EvaluatorFactory;
import gov.va.sparkcql.pipeline.model.CqfKryoRegistrar;
import gov.va.sparkcql.pipeline.model.FhirModelAdapterFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterFactory;
import gov.va.sparkcql.pipeline.preprocessor.FhirSyntheticDataPreprocessorFactory;
import gov.va.sparkcql.pipeline.preprocessor.PreprocessorFactory;
import gov.va.sparkcql.types.QualifiedIdentifier;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FhirIntegratedCapabilityTest extends AbstractTest {

    public FhirIntegratedCapabilityTest() {
        this.configuration
                .writeBinding(CompilerFactory.class, CqfCompilerFactory.class)
                .writeBinding(EvaluatorFactory.class, CqfEvaluatorFactory.class)
                .writeBinding(PreprocessorFactory.class, FhirSyntheticDataPreprocessorFactory.class)
                .writeBinding(ModelAdapterFactory.class, FhirModelAdapterFactory.class);
    }

    @Test
    public void should_prove_fhir_engine_conformity() {

        var r = new CqlPipelineBuilder()
                .withConfig(configuration)
                .evaluate(new QualifiedIdentifier().withId("FhirEngineConformity").withVersion("1.0"))
                .byContext()
                .run()
                .collect();

        r.forEach(System.out::println);
    }
//
//    @Test
//    public void should_prove_quick_engine_conformity() {
//        var pipeline = new Pipeline(getConfiguration());
//        var results = pipeline.execute("QuickEngineConformity", "1.0");
//        showResults(results);
//    }

}