package gov.va.transpiler.bulk.sparksql.node;

import gov.va.transpiler.node.OutputNode;

public abstract class AbstractNodeOneChild extends AbstractNodeWithChildren {

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
