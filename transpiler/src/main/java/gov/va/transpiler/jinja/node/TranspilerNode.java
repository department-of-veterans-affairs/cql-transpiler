package gov.va.transpiler.jinja.node;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public abstract class TranspilerNode {

    private TranspilerNode parent;

    public TranspilerNode(State state) {
        state.setCurrentNode(this);
    }

    /* TREE CONSTRUCTION */

    /**
     * @param parent This node's parent, to allow transversal.
     */
    public void setParent(TranspilerNode parent) {
        this.parent = parent;
    }

    /**
     * @preturn This node's parent.
     */
    public TranspilerNode getParent() {
        return parent;
    }

    /**
     * @param child Child to add to this node.
     * @return True if the child was added. False otherwise.
     */
    public abstract void addChild(TranspilerNode child) throws UnsupportedChildNodeException;

    /* VALUE TYPE */

    /**
     * @return whether this represents a table value
     */
    public abstract boolean isTable();

    /**
     * @return whether this represents a simple value
     */
    public abstract boolean isSimpleValue();

    /**
     * @return A segment to use to print this node.
     */
    public abstract Segment toSegment();

    public String getTargetFileLocation() {
        return getParent() == null ? "" : getParent().getTargetFileLocation();
    }
}
