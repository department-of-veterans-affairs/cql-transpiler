package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.Unary;

public class NegateNode extends Unary {

    @Override
    public String asOneLine() {
        return getChild().isEncapsulated() ? "SELECT -1 * (" + getChild().asOneLine() + ")" : "(-1 * " + getChild().asOneLine() + ")";
    }
}
