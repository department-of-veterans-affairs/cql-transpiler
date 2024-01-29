package gov.va.transpiler.sparkjinja.node.unary;

import org.hl7.elm.r1.As;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

public class AsNode extends Unary<As> {

    public AsNode(State state, As t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        return getChild().toSegment();
    }
}
