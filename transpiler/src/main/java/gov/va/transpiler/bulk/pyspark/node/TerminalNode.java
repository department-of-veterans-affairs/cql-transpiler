package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.node.OutputNode;

public abstract class TerminalNode extends OutputNode{

    @Override
    public boolean addChild(OutputNode child) {
        return false;
    }
}
