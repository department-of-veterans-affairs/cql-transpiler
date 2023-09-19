package gov.va.transpiler.bulk.pyspark.output;

import java.util.ArrayList;
import java.util.List;

import gov.va.transpiler.output.OutputNode;

public class ExpressionDefNode extends OutputNode {

    private Character operator;
    private List<OutputNode> children = new ArrayList<>();

    @Override
    public void addChild(OutputNode child) {
        children.add(child);
    }

    public void setOperator(Character operator) {
        this.operator = operator;
    }

    @Override
    public String asOneLine() {
        String builder = "";
        for (OutputNode child : children) {
            builder += " " + operator;
            builder += " " + child.asOneLine();
        }
        return builder;
    }
}
