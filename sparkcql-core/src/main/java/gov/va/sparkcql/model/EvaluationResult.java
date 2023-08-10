package gov.va.sparkcql.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EvaluationResult implements Serializable {

    private List<Object> evaluatedResources;

    private Map<String, Object> expressionResult;

    public List<Object> getEvaluatedResources() {
        return evaluatedResources;
    }

    public void setEvaluatedResources(List<Object> evaluatedResources) {
        this.evaluatedResources = evaluatedResources;
    }

    public EvaluationResult withEvaluatedResources(List<Object> evaluatedResources) {
        this.evaluatedResources = evaluatedResources;
        return this;
    }

    public Map<String, Object> getExpressionResult() {
        return expressionResult;
    }

    public void setExpressionResult(Map<String, Object> expressionResult) {
        this.expressionResult = expressionResult;
    }

    public EvaluationResult withExpressionResult(Map<String, Object> expressionResult) {
        this.expressionResult = expressionResult;
        return this;
    }
}