package gov.va.sparkcql.pipeline.retriever.resolution;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.ComponentFactory;

public abstract class TableResolutionStrategyFactory extends ComponentFactory {

    public TableResolutionStrategyFactory(Configuration configuration) {
        super(configuration);
    }

    public abstract TableResolutionStrategy create();
}