package gov.va.sparkcql.fixture.sample;

import gov.va.sparkcql.configuration.SystemConfiguration;

public class SampleConfiguration extends SystemConfiguration {

    public SampleConfiguration() {
        this.setResolutionStrategyFormula("${domain}");     // running locally we can't use databases so drop the qualifier
    }
}