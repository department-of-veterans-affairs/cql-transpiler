package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Unary;
import gov.va.transpiler.sparksql.node.ary.RetrieveNode;

public class AliasedQuerySourceNode extends Unary {

    @Override
    public boolean addChild(AbstractCQLNode child) {
        if (child instanceof RetrieveNode) {
            return super.addChild(child);
        }
        return false;
    }

    @Override
    public String asOneLine() {
        return ((RetrieveNode) getChild()).asOneLineWithAlias(getName());
    }
}
