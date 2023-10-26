package gov.va.transpiler.bulk.sparksql.node;

import gov.va.transpiler.node.OutputNode;

public class ReturnClauseNode extends AbstractNodeWithChildren {

    @Override
    public String asOneLine() {
        String builder ="";
        boolean first = true;
        for (OutputNode child : getChildren()) {
            if (!first) {
                builder += ", ";
            }
            builder += child.asOneLine();
            first = false;
        }
        return builder;
    }
}
