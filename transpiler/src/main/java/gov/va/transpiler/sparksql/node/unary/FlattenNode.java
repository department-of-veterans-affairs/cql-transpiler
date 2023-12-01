package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.Unary;

public class FlattenNode extends Unary {

    @Override
    public String asOneLine() {
        return "flatten(" + getChild().asOneLine() + ")";
    }
}
