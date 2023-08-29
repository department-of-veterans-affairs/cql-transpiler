package gov.va.sparkcql.fixture.mock;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategy;

public class MockConfiguration extends EnvironmentConfiguration {

    public MockConfiguration() {
        // Database support unavailable when running locally so drop the qualifier.
        this.writeSetting(TemplateResolutionStrategy.TEMPLATE_RESOLUTION_STRATEGY, "${domain}");
    }
}