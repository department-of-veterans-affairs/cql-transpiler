package gov.va.transpiler.bulk.pyspark.output;

import gov.va.transpiler.output.OutputNode;

public class ExpressionNode extends OutputNode {

    private String name;
    private OutputNode value;

    @Override
    public boolean addChild(OutputNode child) {
        if (this.value == null) {
            value = child;
            return true;
        }
        return false;
    }

    @Override
    public String asOneLine() {
        if (value != null && value.asOneLine() == null) {
            return name == null ? value.asOneLine() : name + " = " + value.asOneLine();
        }
        return null;
    }
}
