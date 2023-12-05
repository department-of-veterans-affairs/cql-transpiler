package gov.va.transpiler.jinja.node.leaf;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;

public abstract class Leaf<T extends Trackable> extends CQLEquivalent<T>  {

    public Leaf(T t) {
        super(t);
    }

    @Override
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
        throw new UnsupportedChildNodeException(this, child);
    }
}
