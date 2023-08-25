package gov.va.sparkcql.pipeline.evaluator;

import java.util.List;
import java.util.Map;

import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.domain.Retrieval;
import gov.va.sparkcql.pipeline.Stage;

public interface Evaluator extends Stage {

    public EvaluatedContext evaluate(String contextElementId, LibraryCollection libraryCollection, Map<Retrieval, List<Object>> clinicalData, Object terminologyData);
}