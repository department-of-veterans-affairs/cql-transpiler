package gov.va.sparkcql.pipeline;

import gov.va.sparkcql.domain.EvaluatedContext;
import gov.va.sparkcql.domain.ExpressionReference;
import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;
import java.util.Map;

public class EvaluationOutput implements Serializable {

    private final JavaRDD<EvaluatedContext> rddByContext;

    public EvaluationOutput(JavaRDD<EvaluatedContext> rdd) {
        this.rddByContext = rdd;
    }

    public JavaRDD<EvaluatedContext> splitByContext() {
        return rddByContext;
    }

    public Map<ExpressionReference, JavaRDD<Object>> splitByExprDef() {
        // TODO: Transpose data so that there's a map of DataSets each with rows spanning all context elements.
        throw new UnsupportedOperationException();
    }
}