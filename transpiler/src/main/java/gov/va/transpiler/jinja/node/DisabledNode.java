package gov.va.transpiler.jinja.node;

import gov.va.transpiler.jinja.printing.Segment;

/**
 * Represents a node that CQL does not actually support. E.g., AccessModifier
 */
public class DisabledNode implements TranspilerNode {

    private TranspilerNode parent;

    @Override
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
        throw new UnsupportedChildNodeException(this, child);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment(this);
        segment.setHead("<Disabled Node>");
        return segment;
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }

    @Override
    public void setParent(TranspilerNode parent) {
        this.parent = parent;
    }

    @Override
    public TranspilerNode getParent() {
        return parent;
    }

    @Override
    public PrintType getPrintType() {
        return PrintType.Line;
    }
}
