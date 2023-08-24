package gov.va.sparkcql.domain;

import scala.Tuple2;

import java.io.Serializable;
import java.util.List;

public class EvaluationResult implements Serializable {

    private String contextId;

    private List<Tuple2<ExpressionReference, List<Object>>> expressionResults;

    private List<Object> evaluated;

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public List<Tuple2<ExpressionReference, List<Object>>> getExpressionResults() {
        return expressionResults;
    }

    public void setExpressionResults(List<Tuple2<ExpressionReference, List<Object>>> expressionResults) {
        this.expressionResults = expressionResults;
    }

    public List<Object> getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(List<Object> evaluated) {
        this.evaluated = evaluated;
    }
}