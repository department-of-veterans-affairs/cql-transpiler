package gov.va.sparkcql.pipeline.retriever.resolution;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.ComponentFactory;

public abstract class TableResolutionStrategyFactory implements ComponentFactory {

    public abstract TableResolutionStrategy create(Configuration configuration);
}