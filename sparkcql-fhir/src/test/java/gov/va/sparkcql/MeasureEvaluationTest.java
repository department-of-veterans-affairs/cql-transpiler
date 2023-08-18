package gov.va.sparkcql;

import org.junit.jupiter.api.Test;

import org.hl7.elm.r1.VersionedIdentifier;

public class MeasureEvaluationTest extends AbstractMeasureTest {

    @Override
    protected void configure() {
        super.configure();
    }

    @Test
    public void should_calc_simple_retrieve_test() {
        execMeasures(new VersionedIdentifier().withId("SIMPLE_QUICK_RETRIEVE").withVersion("1.0"));
    }
}