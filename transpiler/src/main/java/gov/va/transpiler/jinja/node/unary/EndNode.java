package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.End;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class EndNode extends Unary<End> {

    public EndNode(State state, End t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        // TODO: support for reading start/end names from modelinfo
        var segment = new Segment();
        segment.addChild(getChild().toSegment());
        segment.setTail(".?end?");
        return segment;
    }
    
}
