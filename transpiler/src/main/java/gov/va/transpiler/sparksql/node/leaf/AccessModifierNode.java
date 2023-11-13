package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.Leaf;

// Spark SQL doesn't support access modifiers
public class AccessModifierNode extends Leaf {

    @Override
    public void setCqlNodeEquivalent(Object cqlNodeEquivalent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String asOneLine() {
        return null;
    }
}
