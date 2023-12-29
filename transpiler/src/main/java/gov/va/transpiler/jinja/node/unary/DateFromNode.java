package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.DateFrom;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class DateFromNode extends Unary<DateFrom> {

    public DateFromNode(State state, DateFrom t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        return getChild().toSegment();
    }
}
