package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.TupleElement;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class TupleElementNode extends Unary<TupleElement> {

    public TupleElementNode(State state, TupleElement t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("(");
        segment.addChild(containerizer.childToSegmentContainerizing(getChild()));
        segment.setTail(") AS " + getCqlEquivalent().getName());
        return segment;
    }
}
