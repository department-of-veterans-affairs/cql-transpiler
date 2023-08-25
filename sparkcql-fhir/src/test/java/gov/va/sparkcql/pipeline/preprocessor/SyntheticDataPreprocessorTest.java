package gov.va.sparkcql.pipeline.preprocessor;

import gov.va.sparkcql.configuration.LocalSparkFactory;
import gov.va.sparkcql.configuration.SystemConfiguration;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategy;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SyntheticDataPreprocessorTest {

    @Test
    public void should_load_bundles_from_resources() {
        var sparkFactory = new LocalSparkFactory();
        var spark = sparkFactory.create();
        var loader = new SyntheticDataPreprocessor(
                sparkFactory,
                new TemplateResolutionStrategy(new SystemConfiguration()));

        loader.apply();
        var conditionDs = spark.sql("SELECT * FROM condition");
        var encounterDs = spark.sql("SELECT * FROM encounter");
        var patientDs = spark.sql("SELECT * FROM patient");

        assertEquals(3, spark.sqlContext().tableNames().length);

        assertEquals("{http://hl7.org/fhir}Condition", conditionDs.first().getAs("dataType"));

        var encCode = encounterDs.first()
                .<Row>getAs("data")
                .<Row>getAs("class")
                .<String>getAs("code");
        assertEquals("EMER", encCode);

        var gender = patientDs.first()
                .<Row>getAs("data")
                .<String>getAs("gender");
        assertEquals("male", gender);
    }
}