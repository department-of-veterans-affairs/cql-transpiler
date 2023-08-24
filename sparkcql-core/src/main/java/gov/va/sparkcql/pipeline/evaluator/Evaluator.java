package gov.va.sparkcql.pipeline.evaluator;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import gov.va.sparkcql.domain.EvaluationResult;
import gov.va.sparkcql.pipeline.Stage;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Retrieve;
import org.hl7.elm.r1.ValueSetDef;

public interface Evaluator extends Stage {

    public EvaluationResult evaluate(String contextCorrelationId, List<Library> libraries, Map<Retrieve, List<Object>> clinicalData, Map<ValueSetDef, List<Object>> terminologyData);
}