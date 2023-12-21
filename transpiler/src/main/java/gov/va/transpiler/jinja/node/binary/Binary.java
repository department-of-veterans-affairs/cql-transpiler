package gov.va.transpiler.jinja.node.binary;

import org.hl7.elm.r1.BinaryExpression;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.state.State;

public abstract class Binary<T extends BinaryExpression> extends CQLEquivalent<T> {

    private TranspilerNode left;
    private TranspilerNode right;

    public Binary(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (left == null) {
            left = child;
        } else if (right == null) {
            right = child;
        } else {
            throw new UnsupportedChildNodeException(this, child);
        }
    }

    protected TranspilerNode getLeft() {
        return left;
    }

    protected TranspilerNode getRight() {
        return right;
    }
}
