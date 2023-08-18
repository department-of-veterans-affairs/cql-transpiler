package gov.va.sparkcql;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import gov.va.sparkcql.domain.LibraryCollection;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.service.compiler.Compiler;
import gov.va.sparkcql.service.compiler.CqfCompiler;
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
        var libraries = compiler.compile(List.of(new VersionedIdentifier().withId("SIMPLE_QUICK_RETRIEVE").withVersion("1.0")));
        var planner = getInjector().getInstance(Planner.class);
        var plan = planner.plan(libraries);
        var retriever = getInjector().getInstance(BulkRetriever.class);
        var executor = getInjector().getInstance(Executor.class);
        //var clinicalDs = retriever.retrieve(plan, null);
        // var results = executor.execute(new LibraryCollection(libraries), plan, clinicalDs, null);
        // var x = results.collectAsList();
        // results.show(10, false);

//         var parameter = sparkcql.parameter("Measurement Period").dateTimeInterval("2013-01-01", "2014-01-01")
//         assertEvaluation(evaluation)
//         diagnoseEvaluation(evaluation)
    }
}