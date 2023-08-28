package gov.va.sparkcql.fixture;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.Retrieval;

import gov.va.sparkcql.log.Log;
import gov.va.sparkcql.pipeline.evaluator.Evaluator;
import gov.va.sparkcql.domain.ExpressionReference;
import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;
import scala.Tuple2;

public class SampleEvaluator implements Evaluator {

    @Inject
    public SampleEvaluator() {
    }

    @Override
    public EvaluatedContext evaluate(String contextElementId, Map<Retrieval, List<Object>> clinicalData) {
        Log.info("Evaluating Context Element: " + contextElementId);

        var evaluated = clinicalData.values().stream().flatMap(List::stream).toList();
        var expressionReference = new ExpressionReference()
                .withLibrary(new Library().withIdentifier(new VersionedIdentifier().withId("SampleLibrary")))
                .withExpressionDef(new ExpressionDef().withName("Foo"));

        var expressionResults = List.of(Tuple2.apply(expressionReference, evaluated));
        return new EvaluatedContext()
                .withContextId(contextElementId)
                .withEvaluated(evaluated)
                .withExpressionResults(expressionResults);
    }
}