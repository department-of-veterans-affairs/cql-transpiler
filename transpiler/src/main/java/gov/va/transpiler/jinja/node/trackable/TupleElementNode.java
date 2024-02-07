package gov.va.transpiler.jinja.node.trackable;

import org.hl7.elm.r1.TupleElement;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class TupleElementNode extends TrackableNode<TupleElement> {

    private String context;

    public TupleElementNode(State state, TupleElement cqlEquivalent) {
        super(state, cqlEquivalent);
        context = state.getContext();
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    public Type getType() {
        return getChild().getType() == Type.TABLE ? Type.COLLECTED_TABLE : Type.ENCAPSULATED_SIMPLE;
    }

    @Override
    public Segment childToSegment(TranspilerNode child) {
        if (child.getType() == Type.TABLE) {
            return childToSegmentCollectTable(context, child);
        } else if (child.getType() == Type.SIMPLE) {
            return childToSegmentEncapsulateSimple(child);
        } else {
            return super.childToSegment(child);
        }
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead(getName() + "(");
        var nameSegment = new Segment();
        nameSegment.setHead("'" + getCqlEquivalent().getName() + "'");
        nameSegment.setTail(", ");
        segment.addChild(nameSegment);
        segment.addChild(childToSegment(getChild()));
        segment.setTail(")");
        return segment;
    }
}
