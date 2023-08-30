package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.configuration.ServiceModule;
import gov.va.sparkcql.runtime.LocalSparkFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FhirSyntheticDataPreprocessorTest extends ServiceModule {

    @Test
    public void should_mount_synthetic_data() {
        var preprocessor = new FhirSyntheticDataPreprocessor(
                getSparkFactory(),
                new TemplateResolutionStrategy("${domain}"));
        preprocessor.apply();

        var conditionDs = getSpark().sql("select * from condition");
        assertEquals(2, conditionDs.collectAsList().size());
    }
}
