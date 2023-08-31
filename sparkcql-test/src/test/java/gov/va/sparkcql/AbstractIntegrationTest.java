package gov.va.sparkcql;

import gov.va.sparkcql.configuration.ServiceModule;
import gov.va.sparkcql.domain.EvaluationResultSet;

public abstract class AbstractIntegrationTest extends ServiceModule {

    protected void showResults(EvaluationResultSet results) {
        results.splitByContext().collect().forEach(System.out::println);
    }
}
