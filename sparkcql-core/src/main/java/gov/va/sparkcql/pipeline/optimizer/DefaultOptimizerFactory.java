package gov.va.sparkcql.pipeline.optimizer;

import gov.va.sparkcql.configuration.Configuration;

public class DefaultOptimizerFactory extends OptimizerFactory {

    public DefaultOptimizerFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Optimizer create() {
        return new DefaultOptimizer();
    }
}
