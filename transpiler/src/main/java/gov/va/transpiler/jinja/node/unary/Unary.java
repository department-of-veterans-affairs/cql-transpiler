package gov.va.transpiler.jinja.node.unary;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.state.State;

public abstract class Unary<T extends Trackable> extends CQLEquivalent<T> {

    public Unary(State state, T t) {
        super(state, t);
    }

    private TranspilerNode child = null;

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

    protected TranspilerNode getChild() {
        return child;
    }
}
