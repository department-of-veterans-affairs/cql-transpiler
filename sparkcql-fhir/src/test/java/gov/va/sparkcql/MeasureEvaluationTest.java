package gov.va.sparkcql;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import gov.va.sparkcql.service.compiler.Compiler;
import gov.va.sparkcql.service.executor.BulkRetriever;
import gov.va.sparkcql.service.executor.Executor;
import gov.va.sparkcql.service.planner.Planner;

import org.hl7.elm.r1.VersionedIdentifier;

public class MeasureEvaluationTest extends AbstractMeasureTest {

    @Override
    protected void configure() {
        super.configure();
    }

    @Test
    public void should_calc_simple_retrieve_test() {
        assertTrue(true);
        var compiler = getInjector().getInstance(Compiler.class);
        var libraries = compiler.compile(List.of(new VersionedIdentifier().withId("SIMPLE_QUICK_RETRIEVE")));
        
        // var planner = getInjector().getInstance(Planner.class);
        // var plan = planner.plan(libraries);
        // var retriever = getInjector().getInstance(BulkRetriever.class);
        // var executor = getInjector().getInstance(Executor.class);
        // var clinicalDs = retriever.retrieve(plan, null);
        // var results = executor.execute(new LibraryCollection(libraries), plan, clinicalDs, null);
        // var x = results.collectAsList();
        // results.show(10, false);

        // var parameter = sparkcql.parameter("Measurement Period").dateTimeInterval("2013-01-01", "2014-01-01")
        // assertEvaluation(evaluation)
        // diagnoseEvaluation(evaluation)
    }
}