package gov.va.transpiler.bulk.sparksql.node;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.node.OutputNode;

public abstract class AbstractCQLNode<T extends Trackable> extends OutputNode<T> {

    /**
     * We treat tables differently from simple values.
     */
    public boolean isTable() {
        return false;
    }
}
