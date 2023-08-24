package gov.va.sparkcql.service.executor;

import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Retrieve;
import org.hl7.elm.r1.ValueSetDef;

import gov.va.sparkcql.domain.EngineResult;
import gov.va.sparkcql.pipeline.evaluator.Evaluator;

public class CqfEngine implements Evaluator {

    @Override
    public EngineResult evaluate(String contextCorrelationId, List<Library> libraries,
            Map<Retrieve, List<Object>> clinicalData, Map<ValueSetDef, List<Object>> terminologyData) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluate'");
    }
}