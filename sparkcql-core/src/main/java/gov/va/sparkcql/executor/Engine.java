package gov.va.sparkcql.executor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Retrieve;
import org.hl7.elm.r1.ValueSetDef;

import gov.va.sparkcql.entity.EvaluationResult;

public interface Engine extends Serializable {

    public EvaluationResult evaluate(List<Library> libraries, Map<Retrieve, Object> clinicalData, Map<ValueSetDef, Object> terminologyData);
}