package gov.va.transpiler.jinja.node;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.utility.Containerizer;
import gov.va.transpiler.jinja.state.State;

public abstract class CQLEquivalent<T extends Trackable> extends TranspilerNode {

    private T cqlEquivalent;
    protected Containerizer containerizer = new Containerizer();
    private ReferenceableNode referenceTo;

    public CQLEquivalent(State state, T cqlEquivalent) {
        super(state);
        this.cqlEquivalent = cqlEquivalent;
        if (this instanceof ReferenceableNode) {
            state.addReference((ReferenceableNode) this);
        } else if (this instanceof ReferenceNode) {
            referenceTo = state.getReference((ReferenceNode) this);
        }
    }

    public T getCqlEquivalent() {
        return cqlEquivalent;
    }

    protected ReferenceableNode getReferenceTo() {
        return referenceTo;
    }
}
