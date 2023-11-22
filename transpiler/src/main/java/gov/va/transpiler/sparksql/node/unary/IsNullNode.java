package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.Unary;

public class IsNullNode extends Unary {

    @Override
    public String asOneLine() {
        return getChild().asOneLine() + " IS NULL";
    }
}
