package gov.va.sparkcql;

import gov.va.sparkcql.types.QualifiedIdentifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class SparkCqlSessionTest {

    @BeforeEach
    public void setup() {
    }

    @Test
    public void should_provide_fluent_api_for_evaluation() {

        var r = new CqlPipelineBuilder()
                .withSetting("", "")
                .withCql("MyLibrary")
                .withParameter("StartDate", new Date())
                .withPlan(null)
                .evaluate(new QualifiedIdentifier().withId("MyLibrary"))
                .byContext()
                .run();
    }

    @Test
    public void should_provide_fluent_api_for_required_data() {

        var r = new CqlPipelineBuilder()
                .withSetting("", "")
                .withCql("MyLibrary")
                .retrieve(new QualifiedIdentifier().withId("MyLibrary"))
                .byDataType()
                .run();
    }

    @Test
    public void should_provide_fluent_api_for_planning() {

        var r = new CqlPipelineBuilder()
                .withSetting("", "")
                .withCql("MyLibrary")
                .plan(new QualifiedIdentifier().withId("MyLibrary"))
                .run();

    }
}