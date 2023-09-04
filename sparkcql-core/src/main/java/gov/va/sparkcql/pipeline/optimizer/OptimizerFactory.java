package gov.va.sparkcql.pipeline.optimizer;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.pipeline.ComponentFactory;

public abstract class OptimizerFactory extends ComponentFactory {

    public OptimizerFactory(Configuration configuration) {
        super(configuration);
    }

    public abstract Optimizer create();
}