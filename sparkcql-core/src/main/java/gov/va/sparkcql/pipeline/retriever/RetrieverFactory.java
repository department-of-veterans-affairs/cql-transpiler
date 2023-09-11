package gov.va.sparkcql.pipeline.retriever;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.runtime.SparkFactory;
import gov.va.sparkcql.pipeline.ComponentFactory;

public interface RetrieverFactory extends ComponentFactory {

    public abstract Retriever create(Configuration configuration, SparkFactory sparkFactory);
}