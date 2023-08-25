package gov.va.sparkcql.fixture;

import gov.va.sparkcql.configuration.SystemConfiguration;

public class TestConfiguration extends SystemConfiguration {
    public TestConfiguration() {
        this.setCqlSourceFileRepositoryPath("./src/test/resources/cql");
    }
}