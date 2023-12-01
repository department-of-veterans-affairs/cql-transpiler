package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.Unary;

// SortByItemNode is a wrapper
public class SortByItemNode extends Unary {

    @Override
    public String asOneLine() {
        return getChild().asOneLine();
    }
}
