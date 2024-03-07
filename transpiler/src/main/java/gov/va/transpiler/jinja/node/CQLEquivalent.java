package gov.va.transpiler.jinja.node;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public abstract class CQLEquivalent<T extends Trackable> extends TranspilerNode {

    private T cqlEquivalent;
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

    @Override
    public String getOperator() {
        return Standards.macroFileName() + "." + getCqlEquivalent().getClass().getSimpleName();
    }
}
