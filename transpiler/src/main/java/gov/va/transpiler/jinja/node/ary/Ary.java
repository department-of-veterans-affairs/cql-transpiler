package gov.va.transpiler.jinja.node.ary;

import java.util.ArrayList;
import java.util.List;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.converter.State;
import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;

public abstract class Ary<T extends Trackable> extends CQLEquivalent<T> {

    private final List<TranspilerNode> children = new ArrayList<>();

    public Ary(State state, T t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
    if (!(child instanceof DisabledNode)) {
        children.add(child);
    }
    }

    protected List<TranspilerNode> getChildren() {
        return children;
    }
}
