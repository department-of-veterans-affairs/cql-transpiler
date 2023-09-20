package gov.va.transpiler.bulk.pyspark.output.expression;

import gov.va.transpiler.bulk.pyspark.output.ExpressionNode;

public class ValueNode extends ExpressionNode {

    private final String value;

    public ValueNode(String value) {
        this.value = value;
    }

    @Override
    public String asOneLine() {
        return value;
    }
}
