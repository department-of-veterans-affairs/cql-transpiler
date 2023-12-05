package gov.va.transpiler.jinja.node;

import gov.va.transpiler.jinja.printing.Segment;

public interface TranspilerNode {

    /**
     * @param child Child to add to this node.
     * @return True if the child was added. False otherwise.
     */
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException;

    /**
     * @return If this node and all its children can be returned as a single line of text, returns that. Otherwise returns null.
     */
    public Segment toSegment();

    /**
     * @return whether this represents a table value
     */
    public abstract boolean isTable();
}
