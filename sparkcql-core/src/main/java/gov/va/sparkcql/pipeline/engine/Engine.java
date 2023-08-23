package gov.va.sparkcql.pipeline.engine;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Retrieve;
import org.hl7.elm.r1.ValueSetDef;

import gov.va.sparkcql.domain.EngineResult;
import gov.va.sparkcql.pipeline.Component;

public interface Engine extends Component, Serializable {

    public EngineResult evaluate(String contextCorrelationId, List<Library> libraries, Map<Retrieve, List<Object>> clinicalData, Map<ValueSetDef, List<Object>> terminologyData);
}