package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Unary;
import gov.va.transpiler.sparksql.node.ary.RetrieveNode;
import gov.va.transpiler.sparksql.node.unary.PropertyNode;

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
                return "SELECT * FROM (" + getChild().asOneLine() + ") AS " + getName();
            }
        }
        // TODO
        return null;
    }
}
