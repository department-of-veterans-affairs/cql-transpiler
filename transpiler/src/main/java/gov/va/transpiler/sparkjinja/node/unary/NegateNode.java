package gov.va.transpiler.sparkjinja.node.unary;

import org.hl7.elm.r1.Negate;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

public class NegateNode extends Unary<Negate> {

    public NegateNode(State state, Negate t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("-");
        segment.setTail("");
        segment.addChild(getChild().toSegment());
        return segment;
    }
}
