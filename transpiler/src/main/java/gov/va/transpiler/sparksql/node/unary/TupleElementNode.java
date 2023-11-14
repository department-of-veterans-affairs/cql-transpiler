package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Unary;

public class TupleElementNode extends Unary {

    @Override
    public String asOneLine() {
        var builder = getChild().isEncapsulated() || getChild().isTable() ? "(" + childAsOneLineCompressedIfTable((AbstractCQLNode) getChild()) + ")" : getChild().asOneLine();
        return builder + " " + getName();
    }    
}
