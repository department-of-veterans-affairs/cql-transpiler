package gov.va.transpiler.jinja.node;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.state.State;

/**
 * A {@link TranspilerNode} directly equivalent to some node in the CQL AST.
 */
public abstract class CQLEquivalent<T extends Trackable> extends TranspilerNode {

    private final T cqlEquivalent;

    public CQLEquivalent(State state, T cqlEquivalent) {
        super(state);
        this.cqlEquivalent = cqlEquivalent;
    }

    /**
     * @return This node's equivalent node in the CQL AST.
     */
    public T getCqlEquivalent() {
        return cqlEquivalent;
    }

    @Override
    public String getOperator() {
        return getCqlEquivalent().getClass().getSimpleName();
    }
}
