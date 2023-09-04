package gov.va.sparkcql;

import gov.va.sparkcql.configuration.ServiceModule;
import gov.va.sparkcql.pipeline.EvaluationOutput;

public abstract class AbstractIntegrationTest extends ServiceModule {

    protected void showResults(EvaluationOutput results) {
        results.splitByContext().collect().forEach(System.out::println);
    }
}
