package gov.va.sparkcql.mock;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.evaluator.Evaluator;
import gov.va.sparkcql.pipeline.evaluator.EvaluatorFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;

public class MockEvaluatorFactory implements EvaluatorFactory {

    @Override
    public Evaluator create(Configuration configuration, Plan plan, ModelAdapterSet modelAdapterSet, Object terminologyData) {
        return new MockEvaluator();
    }
}