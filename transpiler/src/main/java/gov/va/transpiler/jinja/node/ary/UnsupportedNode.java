package gov.va.transpiler.jinja.node.ary;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;

/**
 * Represents a node we don't support yet.
 */
public class UnsupportedNode extends Ary<Trackable> {

    public UnsupportedNode(Trackable t) {
        super(t);
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
        segment.setPrintType(PrintType.Inline);
        segment.setHead("Unsupported Node from type {" + getCqlEquivalent() + "} with children [");
        for (var child : getChildren()) {
            segment.addSegmentToBody(child.toSegment());
        }
        segment.setTail("]");
        return segment;
    }
}
