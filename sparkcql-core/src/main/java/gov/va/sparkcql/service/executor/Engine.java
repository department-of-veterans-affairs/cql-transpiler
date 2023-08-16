package gov.va.sparkcql.service.executor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Retrieve;
import org.hl7.elm.r1.ValueSetDef;

import gov.va.sparkcql.domain.EngineResult;

public interface Engine extends Serializable {

    public EngineResult evaluate(String contextCorrelationId, List<Library> libraries, Map<Retrieve, List<Object>> clinicalData, Map<ValueSetDef, List<Object>> terminologyData);
}