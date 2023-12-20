package gov.va.transpiler.jinja.node.unsupported;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.ary.Ary;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

/**
 * Represents a node we don't support yet.
 */
public class UnsupportedNode extends Ary<Trackable> {

    public UnsupportedNode(State state, Trackable t) {
        super(state, t);
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
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("Unsupported Node from type {" + getCqlEquivalent().getClass() + "} with children [");
        getChildren().stream().map(TranspilerNode::toSegment).forEach(childSegment -> segment.addChild(childSegment));
        segment.setTail("]");
        return segment;
    }
}
