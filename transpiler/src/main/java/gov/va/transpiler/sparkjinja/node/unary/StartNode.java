package gov.va.transpiler.sparkjinja.node.unary;

import org.hl7.elm.r1.Start;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

public class StartNode extends Unary<Start> {

    public StartNode(State state, Start t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        // TODO: support for reading start/end names from modelinfo
        var segment = new Segment();
        segment.addChild(getChild().toSegment());
        segment.setTail(".?start?");
        return segment;
    }
}
