package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.Leaf;

public class NullNode extends Leaf {

    @Override
    public String asOneLine() {
        return "NULL";
    }
}
