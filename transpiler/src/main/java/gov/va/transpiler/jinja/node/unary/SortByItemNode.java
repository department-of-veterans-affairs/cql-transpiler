package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.SortByItem;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class SortByItemNode extends Unary<SortByItem> {

    public SortByItemNode(State state, SortByItem t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        return getChild().toSegment();
    }
    
}
