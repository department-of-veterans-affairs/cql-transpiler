package gov.va.sparkcql.domain;

import gov.va.sparkcql.types.DataType;
import org.apache.spark.api.java.JavaRDD;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EvaluationResultSet implements Serializable {

    private JavaRDD<EvaluatedContext> rddByContext;

    public EvaluationResultSet(JavaRDD<EvaluatedContext> rdd) {
        this.rddByContext = rdd;
    }

    // TODO: Output Plan

    public JavaRDD<EvaluatedContext> splitByContext() {
        return rddByContext;
    }

    public JavaRDD<EvaluatedExprDef> splitByExprDef() {
        // TODO: Transpose data so that there's a map of DataSets each with
        // rows spanning all context elements.
        throw new UnsupportedOperationException();
    }

    public Map<DataType, List<Object>> splitByDataRequirements() {
        // TODO: A list of all relevant inputs required to calculate the measure for use outside this engine.
        throw new UnsupportedOperationException();
    }

    public Map<ExpressionReference, String> splitByIntersectionMap() {
        // TODO: A map linking all ExpressionDefs and the rows which it produced.
        throw new UnsupportedOperationException();
    }

    // TODO: Consider builder pattern for the two options above (Context/ExprDef, DR/IM/ED).
    // Row by ContextID vs Row by Resource Entity ID vs Row by Expr Def
}