package gov.va.transpiler.sparkjinja.node.unary;

import org.hl7.elm.r1.ToDecimal;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

public class ToDecimalNode extends Unary<ToDecimal> {

    public ToDecimalNode(State state, ToDecimal t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("0.0 + ");
        segment.addChild(getChild().toSegment());
        return segment;
    }
}
