package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.AbstractNodeOneChild;

public class TupleElementNode extends AbstractNodeOneChild {

    @Override
    public String asOneLine() {
        return "(" + childAsOneLineCompressedIfTable((AbstractCQLNode) getChild()) + ") " + getName();
    }    
}
