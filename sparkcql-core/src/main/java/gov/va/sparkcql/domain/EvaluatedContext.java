package gov.va.sparkcql.domain;

import scala.Tuple2;

import java.io.Serializable;
import java.util.List;

public class EvaluatedContext implements Serializable {

    private String contextId;

    private List<Tuple2<ExpressionReference, List<Object>>> expressionResults;

    private List<Object> evaluated;

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public EvaluatedContext withContextId(String contextId) {
        this.contextId = contextId;
        return this;
    }

    public List<Tuple2<ExpressionReference, List<Object>>> getExpressionResults() {
        return expressionResults;
    }

    public void setExpressionResults(List<Tuple2<ExpressionReference, List<Object>>> expressionResults) {
        this.expressionResults = expressionResults;
    }

    public EvaluatedContext withExpressionResults(List<Tuple2<ExpressionReference, List<Object>>> expressionResults) {
        this.expressionResults = expressionResults;
        return this;
    }

    public List<Object> getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(List<Object> evaluated) {
        this.evaluated = evaluated;
    }

    public EvaluatedContext withEvaluated(List<Object> evaluated) {
        this.evaluated = evaluated;
        return this;
    }

    @Override
    public String toString() {
        return "EvaluatedContext{" +
                "contextId='" + contextId + '\'' +
                ", expressionResults=" + expressionResults +
                ", evaluated=" + evaluated +
                '}';
    }
}