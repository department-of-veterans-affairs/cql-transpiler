package gov.va.sparkcql.fixture.sample;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import gov.va.sparkcql.domain.EvaluationResult;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Retrieve;
import org.hl7.elm.r1.ValueSetDef;

import gov.va.sparkcql.log.Log;
import gov.va.sparkcql.pipeline.evaluator.Evaluator;
import gov.va.sparkcql.domain.ExpressionReference;
import gov.va.sparkcql.types.DataType;
import gov.va.sparkcql.types.DataTypedList;

public class SampleEvaluator implements Evaluator {

    @Inject
    public SampleEvaluator() {
    }

    @Override
    public EvaluationResult evaluate(String contextCorrelationId, List<Library> libraries, Map<Retrieve, List<Object>> clinicalData, Map<ValueSetDef, List<Object>> terminologyData) {
        Log.info("Processing Context Correlation ID: " + contextCorrelationId);

        // Echo back some data that was sent in.
        var echo = clinicalData.entrySet().iterator().next();
        var evaluatedResources = List.of(new DataTypedList<Object>()
            .withDataType(new DataType()
                .withNamespaceUri("http://va.gov/sparkcql/sample")
                .withName("Entity"))
            .withValues(List.of(echo.getValue().get(0)))
        );
        
        Map<ExpressionReference,DataTypedList<Object>> expressionResults = Map.ofEntries(
            Map.entry(
                new ExpressionReference()
                    .withLibraryName("SAMPLE_LIBRARY")
                    .withName("Sample Definition A"),
                new DataTypedList<Object>()
                    .withDataType(new DataType(echo.getKey().getDataType()))
                    .withValues(echo.getValue())
            )
        );

        return new EvaluationResult();
//            .withEvaluatedResources(evaluatedResources)
//            .withExpressionResults(expressionResults);
    }
}