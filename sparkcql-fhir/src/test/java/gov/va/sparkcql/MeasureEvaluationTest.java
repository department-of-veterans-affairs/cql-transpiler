package gov.va.sparkcql;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import gov.va.sparkcql.service.compiler.Compiler;

public class MeasureEvaluationTest extends AbstractMeasureTest {

    @Override
    protected void configure() {
        super.configure();
    }
        
    @Test
    public void should_calc_simple_retrieve_test() {
        assertTrue(true);
        var compiler = getInjector().getInstance(Compiler.class);
        
        // var parameter = sparkcql.parameter("Measurement Period").dateTimeInterval("2013-01-01", "2014-01-01")
        // assertEvaluation(evaluation)
        // diagnoseEvaluation(evaluation)
    }
}