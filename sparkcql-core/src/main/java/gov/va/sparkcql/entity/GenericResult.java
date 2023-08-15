package gov.va.sparkcql.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GenericResult<T> implements Serializable {

    private List<T> evaluatedResources;

    private Map<ExpressionReference, T> expressionResults;

    public List<T> getEvaluatedResources() {
        return evaluatedResources;
    }

    public void setEvaluatedResources(List<T> evaluatedResources) {
        this.evaluatedResources = evaluatedResources;
    }

    public GenericResult<T> withEvaluatedResources(List<T> evaluatedResources) {
        this.evaluatedResources = evaluatedResources;
        return this;
    }

    public Map<ExpressionReference, T> getExpressionResults() {
        return expressionResults;
    }

    public void setExpressionResults(Map<ExpressionReference, T> expressionResults) {
        this.expressionResults = expressionResults;
    }

    public GenericResult<T> withExpressionResults(Map<ExpressionReference, T> expressionResults) {
        this.expressionResults = expressionResults;
        return this;
    }
}