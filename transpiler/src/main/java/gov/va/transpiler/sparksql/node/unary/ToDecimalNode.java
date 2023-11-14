package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.Unary;

public class ToDecimalNode extends Unary {

    @Override
    public String asOneLine() {
        return getChild().isEncapsulated() ? "SELECT 0.0 + (" + getChild().asOneLine() + ")" : "(0.0 + " + getChild().asOneLine() + ")";
    }
}
