package gov.va.transpiler.sparkjinja.node.unary;

import org.hl7.elm.r1.Flatten;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

public class FlattenNode extends Unary<Flatten> {

    public FlattenNode(State state, Flatten t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("flatten(");
        segment.addChild(getChild().toSegment());
        segment.setTail(")");
        return segment;
    }
}
