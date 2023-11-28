package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.Unary;

public class EndNode extends Unary {

    @Override
    public String asOneLine() {
        return getChild().asOneLine() + ".end";
    }
}
