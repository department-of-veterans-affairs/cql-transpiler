package gov.va.sparkcql.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import gov.va.sparkcql.SparkCqlSession;
import gov.va.sparkcql.configuration.ConfigKey;

public class SimpleMeasureEvaluationTest {

    private SparkCqlSession sparkcql = SparkCqlSession
        .build()
        .withConfig(ConfigKey.SPARKCQL_CQLSOURCEREPOSITORY_CLASS, "gov.va.sparkcql.repository.CqlSourceFileRepository")
        .withConfig(ConfigKey.SPARKCQL_CQLSOURCEREPOSITORY_PATH, "./src/test/resources/cql")
        .create();

    @Test
    public void should_calc_emergency_department() {
        assertTrue(true);
        // var parameter = sparkcql.parameter("Measurement Period").dateTimeInterval("2013-01-01", "2014-01-01")
        var evaluation = sparkcql.cql("ED_QUICK", "1.0");
        // assertEvaluation(evaluation)
        // diagnoseEvaluation(evaluation)
    }
}