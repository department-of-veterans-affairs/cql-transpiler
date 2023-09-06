package gov.va.sparkcql.mock;

import java.util.List;
import java.util.Map;

import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.Retrieval;

import gov.va.sparkcql.log.Log;
import gov.va.sparkcql.pipeline.evaluator.Evaluator;
import gov.va.sparkcql.domain.ExpressionReference;
import gov.va.sparkcql.types.QualifiedIdentifier;
import org.hl7.elm.r1.ExpressionDef;
import scala.Tuple2;

public class MockEvaluator implements Evaluator {

    public MockEvaluator() {
    }

    @Override
    public EvaluatedContext evaluate(String contextElementId, Map<Retrieval, List<Object>> clinicalData) {
        Log.info("Evaluating Context Element: " + contextElementId);

        var evaluated = clinicalData.values().stream().flatMap(List::stream).toList();
        var expressionReference = new ExpressionReference()
                .withLibraryIdentifier(new QualifiedIdentifier().withId("MockLibrary"))
                .withExpressionDef(new ExpressionDef().withName("Foo"));

        var expressionResults = List.of(Tuple2.apply(expressionReference, evaluated));
        return new EvaluatedContext()
                .withContextId(contextElementId)
                .withExpressionResults(expressionResults);
    }
}