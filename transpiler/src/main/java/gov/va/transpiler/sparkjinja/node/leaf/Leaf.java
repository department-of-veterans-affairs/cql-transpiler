package gov.va.transpiler.sparkjinja.node.leaf;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.sparkjinja.node.CQLEquivalent;
import gov.va.transpiler.sparkjinja.node.TranspilerNode;
import gov.va.transpiler.sparkjinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.sparkjinja.node.unsupported.DisabledNode;
import gov.va.transpiler.sparkjinja.state.State;

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
