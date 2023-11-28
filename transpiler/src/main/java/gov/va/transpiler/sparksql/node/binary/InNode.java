package gov.va.transpiler.sparksql.node.binary;

import gov.va.transpiler.sparksql.node.Binary;
import gov.va.transpiler.sparksql.node.unary.EndNode;
import gov.va.transpiler.sparksql.node.unary.StartNode;

public class InNode extends Binary{

    @Override
    public String asOneLine() {
        var startForChild2 = new StartNode();
        startForChild2.addChild(getChild2());
        var endForChild2 = new EndNode();
        endForChild2.addChild(getChild2());
        return getChild1().asOneLine() + " BETWEEN " + startForChild2.asOneLine() + " AND " + endForChild2.asOneLine();
    }
}
