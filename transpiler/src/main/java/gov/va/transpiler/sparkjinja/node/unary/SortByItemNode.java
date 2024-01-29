package gov.va.transpiler.sparkjinja.node.unary;

import org.hl7.elm.r1.SortByItem;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

public class SortByItemNode extends Unary<SortByItem> {

    public SortByItemNode(State state, SortByItem t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        return getChild().toSegment();
    }
    
}
