package gov.va.transpiler.bulk.sparksql.node;

import gov.va.transpiler.node.OutputNode;

public abstract class AbstractNodeNoChildren extends AbstractCQLNode {

    @Override
    public boolean addChild(OutputNode child) {
        return false;
    }
}
