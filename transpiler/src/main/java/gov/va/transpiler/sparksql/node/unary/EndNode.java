package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.Unary;

// Child is always a period
public class EndNode extends Unary {

    @Override
    public String asOneLine() {
        return getChild().asOneLine() + ".end";
    }
}
