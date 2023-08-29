package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FhirSyntheticDataPreprocessorTest {

    @Test
    public void should_mount_synthetic_data() {
        var sparkFactory = new LocalSparkFactory();
        var preprocessor = new FhirSyntheticDataPreprocessor(
                sparkFactory,
                new TemplateResolutionStrategy(new EnvironmentConfiguration()));
        preprocessor.apply();

        var spark = sparkFactory.create();
        var tables = spark.sqlContext().tableNames();
        var conditionDs = spark.sql("select * from condition");
        assertEquals(2, conditionDs.collectAsList().size());
    }
}
