package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.node.OutputNode;

public abstract class SingleChildNode extends ParentNode {

    @Override
    public boolean addChild(OutputNode child) {
        if (getChildren().isEmpty()) {
            return super.addChild(child);
        }
        return false;
    }

    public OutputNode getChild() {
        return getChildren().get(0);
    }
}
