package gov.va.sparkcql.fixture.sample;

import gov.va.sparkcql.configuration.SystemConfiguration;

public class MockConfiguration extends SystemConfiguration {

    public MockConfiguration() {
        // Database support unavailable when running locally so drop the qualifier.
        this.setResolutionStrategyFormula("${domain}");
    }
}