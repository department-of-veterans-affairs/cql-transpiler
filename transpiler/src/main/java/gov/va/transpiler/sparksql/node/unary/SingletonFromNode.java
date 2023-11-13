package gov.va.transpiler.sparksql.node.unary;

import gov.va.transpiler.sparksql.node.AbstractNodeOneChild;

public class SingletonFromNode extends AbstractNodeOneChild {

    @Override
    public String asOneLine() {
        return getChild().asOneLine();
    }
}
