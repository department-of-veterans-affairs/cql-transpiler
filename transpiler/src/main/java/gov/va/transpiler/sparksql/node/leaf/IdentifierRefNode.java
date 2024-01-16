package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.Leaf;

public class IdentifierRefNode extends Leaf {

    @Override
    public String asOneLine() {
        return getName();
    }

    @Override
    public boolean isColumnReference() {
        return true;
    } 
}
