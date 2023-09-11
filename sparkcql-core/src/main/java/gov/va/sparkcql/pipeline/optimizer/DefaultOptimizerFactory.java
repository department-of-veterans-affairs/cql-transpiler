package gov.va.sparkcql.pipeline.optimizer;

import gov.va.sparkcql.configuration.Configuration;

public class DefaultOptimizerFactory implements OptimizerFactory {

    @Override
    public Optimizer create(Configuration configuration) {
        return new DefaultOptimizer();
    }
}
