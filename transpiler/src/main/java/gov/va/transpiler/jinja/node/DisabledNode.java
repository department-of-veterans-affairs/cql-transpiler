package gov.va.transpiler.jinja.node;

import gov.va.transpiler.jinja.printing.Segment;

/**
 * Represents a node that CQL does not actually support. E.g., AccessModifier
 */
public class DisabledNode implements TranspilerNode {

    @Override
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
        throw new UnsupportedChildNodeException(this, child);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("Disabled Node");
        return segment;
    }

    @Override
    public boolean isTable() {
        return false;
    }
}
