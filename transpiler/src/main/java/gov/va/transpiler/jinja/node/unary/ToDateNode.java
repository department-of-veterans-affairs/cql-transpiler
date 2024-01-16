package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.ToDate;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class ToDateNode extends Unary<ToDate> {

    public ToDateNode(State state, ToDate t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        if (getChild().isSimpleValue()) {
            segment.setHead("CAST (");
            segment.addChild(getChild().toSegment());
            segment.setTail(" AS DATE)");
        } else {
            segment.setHead("CAST ((");
            segment.addChild(getChild().toSegment());
            segment.setTail(") AS DATE)");
        }
        return segment;
    }
}
