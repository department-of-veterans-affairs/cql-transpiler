package gov.va.transpiler.jinja.node.unary;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;

public abstract class Unary<T extends Trackable> extends CQLEquivalent<T> {

    public Unary(T t) {
        super(t);
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

    protected TranspilerNode getChild() {
        return child;
    }
}
