package gov.va.sparkcql.pipeline.retriever.resolution;

import gov.va.sparkcql.configuration.Configuration;

public class TemplateResolutionStrategyFactory extends TableResolutionStrategyFactory {

    public static final String TEMPLATE_RESOLUTION_STRATEGY = "sparkcql.resolutionstrategy.template";

    public TemplateResolutionStrategyFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public TableResolutionStrategy create() {
        var template = getConfiguration().readSetting(TEMPLATE_RESOLUTION_STRATEGY, "${model}.${domain}");
        return new TemplateResolutionStrategy(template);
    }
}
