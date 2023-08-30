package gov.va.sparkcql.pipeline.evaluator;

import gov.va.sparkcql.configuration.Configuration;
import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.configuration.ComponentFactory;
import gov.va.sparkcql.pipeline.model.ModelAdapterComposite;

public abstract class EvaluatorFactory extends ComponentFactory {

    public EvaluatorFactory(Configuration configuration) {
        super(configuration);
    }

    public abstract Evaluator create(Plan plan, ModelAdapterComposite modelAdapterComposite, Object terminologyData);
}