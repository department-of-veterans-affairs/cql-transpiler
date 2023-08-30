package gov.va.sparkcql.fixture.mock;

import gov.va.sparkcql.configuration.EnvironmentConfiguration;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategy;
import gov.va.sparkcql.pipeline.retriever.resolution.TemplateResolutionStrategyFactory;

public class MockConfiguration extends EnvironmentConfiguration {

    public MockConfiguration() {
        // Database support unavailable when running locally so drop the qualifier.
        this.writeSetting(TemplateResolutionStrategyFactory.TEMPLATE_RESOLUTION_STRATEGY, "${domain}");
    }
}