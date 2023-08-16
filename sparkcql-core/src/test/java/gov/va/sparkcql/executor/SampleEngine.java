package gov.va.sparkcql.executor;

import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Retrieve;
import org.hl7.elm.r1.ValueSetDef;

import gov.va.sparkcql.common.log.Log;
import gov.va.sparkcql.entity.DataType;
import gov.va.sparkcql.entity.DataTypedList;
import gov.va.sparkcql.entity.EngineResult;
import gov.va.sparkcql.entity.ExpressionReference;

public class SampleEngine implements Engine {

    @Override
    public EngineResult evaluate(String contextCorrelationId, List<Library> libraries, Map<Retrieve, List<Object>> clinicalData, Map<ValueSetDef, List<Object>> terminologyData) {
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

        return new EngineResult()
            .withEvaluatedResources(evaluatedResources)
            .withExpressionResults(expressionResults);
    }
}