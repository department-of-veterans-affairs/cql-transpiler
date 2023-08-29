package gov.va.sparkcql.fixture.fhir;

import gov.va.sparkcql.configuration.SystemConfiguration;

public class FhirConfiguration extends SystemConfiguration {
    public FhirConfiguration() {
        this.setCqlSourceFileRepositoryPath("./src/test/resources/cql");
    }
}