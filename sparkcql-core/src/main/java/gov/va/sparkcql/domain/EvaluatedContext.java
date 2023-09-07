package gov.va.sparkcql.domain;

import scala.Tuple2;

import java.io.Serializable;
import java.util.List;

public class EvaluatedContext implements Serializable {

    private String contextId;

    private List<Tuple2<ExpressionReference, List<Object>>> expressionResults;

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

//    private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
//        // Kryo deserialization of nested objects which may not support Serializable.
//        var o = Kryos.read(input, EvaluatedContext.class);
//        this.contextId = o.contextId;
//        this.expressionResults = o.expressionResults;
//    }
//
//    private void writeObject(ObjectOutputStream output) throws IOException {
//        // Kryo serialization of nested objects which may not support Serializable.
//        Kryos.write(output, this);
//    }
}