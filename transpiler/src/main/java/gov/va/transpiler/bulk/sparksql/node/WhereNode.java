package gov.va.transpiler.bulk.sparksql.node;

import org.cqframework.cql.elm.tracking.Trackable;

// WhereNode is a wrapper
public class WhereNode extends AbstractNodeWithChildren<Trackable> {

    @Override
    public void setCqlNodeEquivalent(Trackable cqlNodeEquivalent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String asOneLine() {
        String builder = "WHERE (";
        boolean first = true;
        for (var child : getChildren()) {
            if (first) {
                first = false;
            } else {
                builder += " AND (";
            }
            builder += child.asOneLine();
            builder += ")";
        }
        return builder;
    }
}
