package gov.va.sparkcql.domain;

import gov.va.sparkcql.types.QualifiedIdentifier;
import gov.va.sparkcql.types.Tuple;
import scala.Tuple2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EvaluatedContext implements Serializable {

    private String contextId;

    private List<Tuple2<ExpressionReference, List<Object>>> expressionResults;

    private List<QualifiedIdentifier> myData;

    public List<QualifiedIdentifier> getMyData() {
        return myData;
    }

    public void setMyData(List<QualifiedIdentifier> myData) {
        this.myData = myData;
    }

    public EvaluatedContext withMyData(List<QualifiedIdentifier> myData) {
        this.myData = myData;
        return this;
    }

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

    @Override
    public String toString() {
        var size = 0;
        if (expressionResults != null)
            size = expressionResults.size();

        return "EvaluatedContext{" +
                "contextId='" + contextId + '\'' +
                ", resultCount=" + size +
                '}';
    }
}