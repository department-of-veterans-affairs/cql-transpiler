package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.AbstractNodeOneChild;

public class ReturnClauseNode extends AbstractNodeOneChild {

    @Override
    public String asOneLine() {
        return getChild().asOneLine();
    }
}
