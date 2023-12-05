package gov.va.transpiler.jinja.node.ary;

import java.util.ArrayList;
import java.util.List;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;

public abstract class Ary<T extends Trackable> extends CQLEquivalent<T> {

    private final List<TranspilerNode> children = new ArrayList<>();

    public Ary(T t) {
        super(t);
    }

    @Override
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
        children.add(child);
    }

    protected List<TranspilerNode> getChildren() {
        return children;
    }
}
