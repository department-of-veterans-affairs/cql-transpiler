package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.configuration.Injector;
import gov.va.sparkcql.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.ComponentFactory;
import gov.va.sparkcql.pipeline.retriever.resolution.TableResolutionStrategyFactory;

public interface RetrieverFactory extends ComponentFactory {

    public abstract Retriever create(Configuration configuration, SparkFactory sparkFactory);
}