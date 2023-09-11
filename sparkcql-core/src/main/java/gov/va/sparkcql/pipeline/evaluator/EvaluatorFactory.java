package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.ComponentFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterSet;

public interface EvaluatorFactory extends ComponentFactory {

    public Evaluator create(Configuration configuration, Plan plan, ModelAdapterSet modelAdapterSet, Object terminologyData);
}