package gov.va.sparkcql.executor;

import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.Retrieve;
import org.hl7.elm.r1.ValueSetDef;

import gov.va.sparkcql.entity.EvaluationResult;

public class SampleEngine implements Engine {

    @Override
    public EvaluationResult evaluate(List<Library> libraries, Map<Retrieve, Object> clinicalData,
            Map<ValueSetDef, Object> terminologyData) {

        return new EvaluationResult()
            .withEvaluatedResources(List.of("Hello"))
            .withExpressionResult(Map.of("Sample Definition A", clinicalData));
    }
}