package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.AbstractNodeNoChildren;

public class AccessModifierNode extends AbstractNodeNoChildren {

    @Override
    public void setCqlNodeEquivalent(Object cqlNodeEquivalent) {
        throw new UnsupportedOperationException();
    }

    // Spark SQL doesn't support access modifiers
    @Override
    public String asOneLine() {
        return null;
    }
}
