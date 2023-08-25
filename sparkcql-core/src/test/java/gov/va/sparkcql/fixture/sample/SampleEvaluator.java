package gov.va.sparkcql.fixture.sample;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.domain.Retrieval;

import gov.va.sparkcql.log.Log;
import gov.va.sparkcql.pipeline.evaluator.Evaluator;
import gov.va.sparkcql.domain.ExpressionReference;
import scala.Tuple2;

public class SampleEvaluator implements Evaluator {

    @Inject
    public SampleEvaluator() {
    }

    @Override
    public EvaluatedContext evaluate(String contextElementId, LibraryCollection libraryCollection, Map<Retrieval, List<Object>> clinicalData, Object terminologyData) {
        Log.info("Evaluating Context Element: " + contextElementId);

        var evaluated = clinicalData.values().stream().flatMap(List::stream).toList();
        var expressionReference = new ExpressionReference()
                .withLibrary(libraryCollection.get(0))
                .withExpressionDef(libraryCollection.get(0).getStatements().getDef().get(0));

        var expressionResults = List.of(Tuple2.apply(expressionReference, evaluated));
        return new EvaluatedContext()
                .withContextId(contextElementId)
                .withEvaluated(evaluated)
                .withExpressionResults(expressionResults);
    }
}