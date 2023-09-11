package gov.va.sparkcql.pipeline.retriever.resolution;

import gov.va.sparkcql.configuration.Configuration;

public class TemplateResolutionStrategyFactory extends TableResolutionStrategyFactory {

    public static final String TEMPLATE_RESOLUTION_STRATEGY = "sparkcql.resolutionstrategy.template";

    @Override
    public TableResolutionStrategy create(Configuration configuration) {
        var template = configuration.readSetting(TEMPLATE_RESOLUTION_STRATEGY, "${model}.${domain}");
        return new TemplateResolutionStrategy(template);
    }
}
