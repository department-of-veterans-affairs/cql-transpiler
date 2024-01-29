package gov.va.transpiler.sparkjinja.node.unary;

import org.hl7.elm.r1.DateFrom;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

public class DateFromNode extends Unary<DateFrom> {

    public DateFromNode(State state, DateFrom t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        return getChild().toSegment();
    }
}
