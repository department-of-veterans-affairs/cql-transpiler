package gov.va.transpiler.jinja.node.wrapper;

import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public abstract class Wrapper extends TranspilerNode {

    public Wrapper(State state) {
        super(state);
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

    protected TranspilerNode getChild() {
        return child;
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
    public Segment toSegment() {
        return getChild().toSegment();
    }
}
