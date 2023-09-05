package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.AbstractTest;
import gov.va.sparkcql.pipeline.model.FhirModelAdapter;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FhirSyntheticDataPreprocessorTest extends AbstractTest {

    @Test
    public void should_mount_synthetic_data() {
        var preprocessor = new FhirSyntheticDataPreprocessor(
                configuration,
                sparkFactory,
                new TemplateResolutionStrategy("${domain}"),
                new ModelAdapterSet(List.of(new FhirModelAdapter())));
        preprocessor.apply();

        var conditionDs = spark.sql("select * from condition");
        assertEquals(2, conditionDs.collectAsList().size());
    }
}
