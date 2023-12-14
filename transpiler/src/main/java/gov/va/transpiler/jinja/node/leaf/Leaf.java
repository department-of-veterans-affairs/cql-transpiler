package gov.va.transpiler.jinja.node.leaf;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.converter.State;
import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;

public abstract class Leaf<T extends Trackable> extends CQLEquivalent<T>  {

    public Leaf(State state, T t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
        if (!(child instanceof DisabledNode)) {
            throw new UnsupportedChildNodeException(this, child);
        }
    }
}
