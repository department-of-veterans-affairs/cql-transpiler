package gov.va.transpiler.sparksql.node.ary;

import gov.va.transpiler.sparksql.node.Ary;

// WhereNode is a wrapper
public class WhereNode extends Ary {

    @Override
    public void setCqlNodeEquivalent(Object cqlNodeEquivalent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String asOneLine() {
        String builder = "WHERE ";
        boolean first = true;
        for (var child : getChildren()) {
            if (first) {
                first = false;
            } else {
                builder += " AND ";
            }
            builder += child.asOneLine();
        }
        return builder;
    }
}
