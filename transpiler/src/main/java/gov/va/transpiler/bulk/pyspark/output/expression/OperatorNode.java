package gov.va.transpiler.bulk.pyspark.output.expression;

import java.util.ArrayList;
import java.util.List;

import gov.va.transpiler.bulk.pyspark.output.ExpressionNode;
import gov.va.transpiler.output.OutputNode;

public class OperatorNode extends ExpressionNode {
    
    private final String operator;

    private final List<OutputNode> children;

    public OperatorNode(String operator) {
        this.operator = operator;
        children = new ArrayList<>();
    }

    @Override
    public void addChild(OutputNode child) {
        children.add(child);
    }

    @Override
    public String asOneLine() {
        if (children.isEmpty()) {
            return null;
        }
        String builder = "";
        boolean first = true;
        for (var child : children) {
            builder += first ? child.asOneLine() : " " + operator + " " + child.asOneLine();
            first = false;
        }
        return builder;
    }
}
