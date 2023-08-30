package gov.va.sparkcql.pipeline.evaluator;

import java.util.List;
import java.util.Map;

import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.configuration.Component;

public interface Evaluator extends Component {

    public EvaluatedContext evaluate(String contextElementId, Map<Retrieval, List<Object>> clinicalData);
}