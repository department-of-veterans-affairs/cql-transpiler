package gov.va.sparkcql.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EvaluationResult implements Serializable {

    private List<String> evaluatedResources;

    private Map<String, ?> expressionResult;

    public List<String> getEvaluatedResources() {
        return evaluatedResources;
    }

    public void setEvaluatedResources(List<String> evaluatedResources) {
        this.evaluatedResources = evaluatedResources;
    }

    public EvaluationResult withEvaluatedResources(List<String> evaluatedResources) {
        this.evaluatedResources = evaluatedResources;
        return this;
    }

    public Map<String, ?> getExpressionResult() {
        return expressionResult;
    }

    public void setExpressionResult(Map<String, ?> expressionResult) {
        this.expressionResult = expressionResult;
    }

    public EvaluationResult withExpressionResult(Map<String, ?> expressionResult) {
        this.expressionResult = expressionResult;
        return this;
    }
}