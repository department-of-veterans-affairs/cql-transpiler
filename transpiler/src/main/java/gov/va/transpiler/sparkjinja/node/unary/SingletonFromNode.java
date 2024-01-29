package gov.va.transpiler.sparkjinja.node.unary;

import org.hl7.elm.r1.SingletonFrom;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

public class SingletonFromNode extends Unary<SingletonFrom> {

    public SingletonFromNode(State state, SingletonFrom t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        return getChild().toSegment();
    }
}
