package gov.va.transpiler.sparkjinja.node.unsupported;

import gov.va.transpiler.sparkjinja.node.TranspilerNode;
import gov.va.transpiler.sparkjinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

/**
 * Represents a node that CQL does not actually support. E.g., AccessModifier
 */
public class DisabledNode extends TranspilerNode {

    public DisabledNode(State state) {
        super(state);
    }

    @Override
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
        throw new UnsupportedChildNodeException(this, child);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
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
}
