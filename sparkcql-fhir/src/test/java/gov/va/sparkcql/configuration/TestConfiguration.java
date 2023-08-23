package gov.va.sparkcql.configuration;

public class TestConfiguration extends SystemConfiguration {
    public TestConfiguration() {
        this.setCqlSourceFileRepositoryPath("./src/test/resources/cql");
    }
}