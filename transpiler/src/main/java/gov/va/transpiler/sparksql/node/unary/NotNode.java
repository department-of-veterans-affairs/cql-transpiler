package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.Unary;

public class NotNode extends Unary {

    @Override
    public String asOneLine() {
        return "NOT (" + getChild().asOneLine() + ")";
    }
}
