package gov.va.transpiler.sparkjinja.node.unary;

import org.hl7.elm.r1.TupleElement;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

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
