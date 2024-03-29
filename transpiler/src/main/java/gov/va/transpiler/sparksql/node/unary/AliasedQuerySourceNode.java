package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Unary;
import gov.va.transpiler.sparksql.node.ary.RetrieveNode;

public class AliasedQuerySourceNode extends Unary {

    PropertyNode propertyNode;

    @Override
    public boolean addChild(AbstractCQLNode child) {
        if (child instanceof PropertyNode) {
            propertyNode = (PropertyNode) child;
            return true;
        }
        return super.addChild(child);
    }

    @Override
    public String asOneLine() {
        if (propertyNode == null) {
            if(getChild() instanceof RetrieveNode) {
                return ((RetrieveNode) getChild()).asOneLineWithAlias(getName());
            } else {
                return getChild().asOneLine() + " AS " + getName();
            }
        }
        // TODO
        return null;
    }
}
