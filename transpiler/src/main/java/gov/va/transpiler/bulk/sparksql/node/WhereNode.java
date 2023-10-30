package gov.va.transpiler.bulk.sparksql.node;

// WhereNode is a wrapper
public class WhereNode extends AbstractNodeWithChildren {

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
