package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.model.ModelAdapterResolver;

public interface EvaluatorFactory {

    public Evaluator create(Plan plan, ModelAdapterResolver modelAdapterResolver, Object terminologyData);
}