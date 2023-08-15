package gov.va.sparkcql.executor;

import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Retrieve;
import org.hl7.elm.r1.ValueSetDef;

import gov.va.sparkcql.common.log.Log;
import gov.va.sparkcql.entity.EngineResult;
import gov.va.sparkcql.entity.ExpressionReference;
import gov.va.sparkcql.entity.GenericTypeSpecifiedElement;

public class SampleEngine implements Engine {

    @Override
    public EngineResult evaluate(String contextCorrelationId, List<Library> libraries, Map<Retrieve, List<Object>> clinicalData, Map<ValueSetDef, List<Object>> terminologyData) {
        Log.info("Processing Context Correlation ID: " + contextCorrelationId);

        // Echo back some data that was sent in.
        var echo = clinicalData.entrySet().iterator().next();
        var evaluatedResources = List.of(
                new GenericTypeSpecifiedElement<Object>()
                    .withResultTypeName(echo.getKey().getDataType())
                    .withResultValue(echo.getValue().get(0)));
        
        Map<ExpressionReference,GenericTypeSpecifiedElement<Object>> expressionResults = Map.ofEntries(
            Map.entry(
                new ExpressionReference()
                    .withLibraryName("SAMPLE_LIBRARY")
                    .withName("Sample Definition A"),
                new GenericTypeSpecifiedElement<Object>()
                    .withResultTypeName(echo.getKey().getDataType())
                    .withResultValue(echo.getValue().get(0))
            )
        );

        return (EngineResult) new EngineResult()
            .withEvaluatedResources(evaluatedResources)
            .withExpressionResults(expressionResults);
    }
}