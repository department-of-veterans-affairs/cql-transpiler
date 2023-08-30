package gov.va.sparkcql.domain;

import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;

public class EvaluationResultSet implements Serializable {

    private JavaRDD<EvaluatedContext> rddByContext;

    public EvaluationResultSet(JavaRDD<EvaluatedContext> rdd) {
        this.rddByContext = rdd;
    }

    public JavaRDD<EvaluatedContext> splitByContext() {
        return rddByContext;
    }

    public JavaRDD<EvaluatedExprDef> splitByExprDef() {
        // TODO: Transpose data so that there's a map of DataSets each with
        // rows spanning all context elements.
        throw new UnsupportedOperationException();
    }
}