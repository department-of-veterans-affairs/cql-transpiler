package gov.va.sparkcql.fixture.mock;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.evaluator.Evaluator;
import gov.va.sparkcql.pipeline.evaluator.EvaluatorFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;

public class MockEvaluatorFactory extends EvaluatorFactory {

    public MockEvaluatorFactory(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Evaluator create(Plan plan, ModelAdapterSet modelAdapterSet, Object terminologyData) {
        return new MockEvaluator();
    }
}