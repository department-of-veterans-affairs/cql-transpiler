package gov.va.sparkcql.fixture.sample;

import gov.va.sparkcql.configuration.SystemConfiguration;

public class SampleConfiguration extends SystemConfiguration {

    public SampleConfiguration() {
        // Database support unavailable when running locally so drop the qualifier.
        this.setResolutionStrategyFormula("${domain}");
    }
}