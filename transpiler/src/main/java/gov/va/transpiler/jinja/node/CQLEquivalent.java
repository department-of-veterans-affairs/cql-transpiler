package gov.va.transpiler.jinja.node;

import org.cqframework.cql.elm.tracking.Trackable;

public abstract class CQLEquivalent<T extends Trackable> implements TranspilerNode {

    private T cqlEquivalent;
    private TranspilerNode parent;

    public CQLEquivalent(T t) {
        cqlEquivalent = t;
    }

    public T getCqlEquivalent() {
        return cqlEquivalent;
    }

    @Override
    public void setParent(TranspilerNode parent) {
        this.parent = parent;
    }

    @Override
    public TranspilerNode getParent() {
        return parent;
    }
}
