package gov.va.transpiler.bulk.sparksql.node;

import gov.va.transpiler.node.TerminalNode;

public class ExpressionRefNode extends TerminalNode {

    @Override
    public String asOneLine() {
        return "SELECT * FROM " + getName();
    }
}
