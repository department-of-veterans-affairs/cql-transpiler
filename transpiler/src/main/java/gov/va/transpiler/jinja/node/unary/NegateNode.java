package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.Negate;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class NegateNode extends Unary<Negate> {

    public NegateNode(State state, Negate t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("(-");
        segment.setTail(")");
        segment.addChild(getChild().toSegment());
        return segment;
    }
}
