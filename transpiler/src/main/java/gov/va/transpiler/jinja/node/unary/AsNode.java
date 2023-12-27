package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.As;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class AsNode extends Unary<As> {

    public AsNode(State state, As t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        return getChild().toSegment();
    }
}
