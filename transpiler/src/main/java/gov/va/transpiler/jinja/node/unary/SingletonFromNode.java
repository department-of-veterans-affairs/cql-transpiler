package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.SingletonFrom;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class SingletonFromNode extends Unary<SingletonFrom> {

    public SingletonFromNode(State state, SingletonFrom t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        return getChild().toSegment();
    }
}
