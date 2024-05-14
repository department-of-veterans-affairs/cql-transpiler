package gov.va.transpiler.jinja.node;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;
import gov.va.transpiler.jinja.state.State;

/**
 * A {@link TranspilerNode} directly equivalent to some node in the CQL AST.
 */
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

    /**
     * @return This node's equivalent node in the CQL AST.
     */
    public T getCqlEquivalent() {
        return cqlEquivalent;
    }

    /**
     * @return If this node is a {@link ReferenceNode}, returns the node this node is a reference to. Otherwise returns null.
     */
    protected ReferenceableNode getReferenceTo() {
        return referenceTo;
    }

    @Override
    public String getOperator() {
        return getCqlEquivalent().getClass().getSimpleName();
    }
}
