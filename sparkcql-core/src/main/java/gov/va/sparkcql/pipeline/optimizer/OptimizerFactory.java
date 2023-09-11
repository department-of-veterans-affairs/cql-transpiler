package gov.va.sparkcql.pipeline.optimizer;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.ComponentFactory;

public interface OptimizerFactory extends ComponentFactory {

    public abstract Optimizer create(Configuration configuration);
}