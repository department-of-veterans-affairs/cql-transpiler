package gov.va.sparkcql.entity;

import java.util.List;
import java.util.Map;

public class GenericResult<T, S extends GenericResult<T, S>> {

    private List<T> evaluatedResources;

    private Map<ExpressionReference, T> expressionResults;

    public List<T> getEvaluatedResources() {
        return evaluatedResources;
    }

    public void setEvaluatedResources(List<T> evaluatedResources) {
        this.evaluatedResources = evaluatedResources;
    }

    @SuppressWarnings("unchecked")
    public S withEvaluatedResources(List<T> evaluatedResources) {
        this.evaluatedResources = evaluatedResources;
        return (S)this;
    }

    public Map<ExpressionReference, T> getExpressionResults() {
        return expressionResults;
    }

    public void setExpressionResults(Map<ExpressionReference, T> expressionResults) {
        this.expressionResults = expressionResults;
    }

    @SuppressWarnings("unchecked")
    public S withExpressionResults(Map<ExpressionReference, T> expressionResults) {
        this.expressionResults = expressionResults;
        return (S)this;
    }
}