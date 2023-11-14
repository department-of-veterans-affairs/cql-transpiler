package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Leaf;
import gov.va.transpiler.sparksql.node.ary.RetrieveNode;
import gov.va.transpiler.sparksql.node.unary.PropertyNode;

public class AliasedQuerySourceNode extends Leaf {

    RetrieveNode retrieveNode;
    PropertyNode propertyNode;

    @Override
    public boolean addChild(AbstractCQLNode child) {
        if (child instanceof RetrieveNode) {
            retrieveNode = (RetrieveNode) child;
            return true;
        } else if (child instanceof PropertyNode) {
            propertyNode = (PropertyNode) child;
            return true;
        }
        return false;
    }

    @Override
    public String asOneLine() {
        if (retrieveNode != null && propertyNode == null) {
            return retrieveNode.asOneLineWithAlias(getName());
        }
        // TODO
        return null;
    }
}
