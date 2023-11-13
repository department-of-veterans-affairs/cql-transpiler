package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.AbstractNodeOneChild;

// Child is always a period
public class EndNode extends AbstractNodeOneChild {

    @Override
    public String asOneLine() {
        return getChild().asOneLine() + ".end";
    }
}
