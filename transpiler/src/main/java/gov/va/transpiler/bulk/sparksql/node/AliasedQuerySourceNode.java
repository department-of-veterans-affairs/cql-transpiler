package gov.va.transpiler.bulk.sparksql.node;

import gov.va.transpiler.node.OutputNode;

public class AliasedQuerySourceNode extends AbstractNodeOneChild {

    @Override
    public boolean addChild(OutputNode child) {
        if (child instanceof RetrieveNode) {
            return super.addChild(child);
        }
        return false;
    }

    @Override
    public String asOneLine() {
        return getChild().getName() + " AS " + getName();
    }
}
