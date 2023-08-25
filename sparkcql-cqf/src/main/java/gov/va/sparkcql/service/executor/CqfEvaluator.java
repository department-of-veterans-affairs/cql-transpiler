package gov.va.sparkcql.service.executor;

import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.pipeline.evaluator.Evaluator;

import java.util.List;
import java.util.Map;

public class CqfEvaluator implements Evaluator {
    @Override
    public EvaluatedContext evaluate(String contextElementId, LibraryCollection libraryCollection, Map<Retrieval, List<Object>> clinicalData, Object terminologyData) {
        return null;
    }
}