package gov.va.transpiler.sparkjinja.node.unary;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.sparkjinja.node.CQLEquivalent;
import gov.va.transpiler.sparkjinja.node.TranspilerNode;
import gov.va.transpiler.sparkjinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.sparkjinja.node.unsupported.DisabledNode;
import gov.va.transpiler.sparkjinja.state.State;

public abstract class Unary<T extends Trackable> extends CQLEquivalent<T> {

    private TranspilerNode child = null;

    public Unary(State state, T t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
        if (!(child instanceof DisabledNode)) {
            if (this.child == null) {
                this.child = child;
            } else {
                throw new UnsupportedChildNodeException(this, child);
            }
        }
    }

    @Override
    public boolean isSimpleValue() {
        return getChild().isSimpleValue();
    }

    @Override
    public boolean isTable() {
        return getChild().isTable();
    }

    @Override
    public boolean isColumnReference() {
        return getChild().isColumnReference();
    }

    protected TranspilerNode getChild() {
        return child;
    }
}
