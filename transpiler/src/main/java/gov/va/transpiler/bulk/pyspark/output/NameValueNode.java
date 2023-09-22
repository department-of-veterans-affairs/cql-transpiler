package gov.va.transpiler.bulk.pyspark.output;

import gov.va.transpiler.output.OutputNode;

public abstract class NameValueNode extends OutputNode {

    private VariableNameNode name;
    private OutputNode value;

    public void setName(VariableNameNode name) {
        this.name = name;
    }

    protected VariableNameNode getName() {
        return name;
    }

    @Override
    public boolean addChild(OutputNode child) {
        if (value == null) {
            value = child;
            return true;
        }

        return false;
    }

    protected OutputNode getValue() {
        return value;
    }
}
