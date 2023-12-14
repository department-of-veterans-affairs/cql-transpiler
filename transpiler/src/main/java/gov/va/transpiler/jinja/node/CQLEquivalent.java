package gov.va.transpiler.jinja.node;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.converter.State;

public abstract class CQLEquivalent<T extends Trackable> extends TranspilerNode {

    private T cqlEquivalent;
    private TranspilerNode parent;

    public CQLEquivalent(State state, T cqlEquivalent) {
        super(state);
        this.cqlEquivalent = cqlEquivalent;
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
