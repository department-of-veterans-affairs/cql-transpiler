package gov.va.transpiler.bulk.sparksql.node;

import gov.va.transpiler.node.OutputNode;

public abstract class AbstractCQLNode extends OutputNode {
    /**
     * We treat tables differently from simple values.
     */
    public boolean isTable() {
        return false;
    }
}
