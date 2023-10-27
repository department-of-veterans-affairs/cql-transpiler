package gov.va.transpiler.bulk.sparksql.node;

import gov.va.transpiler.bulk.sparksql.utilities.Standards;
import gov.va.transpiler.node.OutputNode;

public class TupleNode extends AbstractNodeWithChildren {

    @Override
    public boolean addChild(OutputNode child) {
        if (child instanceof TupleElementNode) {
            return super.addChild(child);
        }
        return false;
    }

    @Override
    public String asOneLine() {
        if (!getChildren().isEmpty()) {
            String builder = "SELECT struct(";
            boolean first = true;
            for (OutputNode child : getChildren()) {
                if (first) {
                    first = false;
                } else {
                    builder += ", ";
                }
                builder += child.asOneLine();
            }
            builder += ") AS _val";
            return builder;
        }
        return Standards.EMPTY_TABLE;
    }
}
