package gov.va.transpiler.jinja.node.ary.binary;

import org.hl7.elm.r1.DifferenceBetween;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class DifferenceBetweenNode extends Binary<DifferenceBetween> {

    public DifferenceBetweenNode(State state, DifferenceBetween t) {
        super(state, t);
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public boolean isSimpleValue() {
        return true;
    }

    @Override
    public Segment toSegment() {
        switch (getCqlEquivalent().getPrecision()) {
            case DAY:
                return toSegmentWithJoinedChildren("datediff(", ")", "", "", ", ", ",");
            default:
                throw new UnsupportedOperationException("We don't current support precision of type " + getCqlEquivalent().getPrecision());
        }
    }
}
