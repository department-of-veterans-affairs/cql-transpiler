package gov.va.transpiler.jinja.node.ary;

import org.hl7.elm.r1.SortClause;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class SortClauseNode extends Ary<SortClause> {

    public SortClauseNode(State state, SortClause t) {
        super(state, t);
    }

    @Override
    public boolean isTable() {
        throw new UnsupportedOperationException("Not a standalone node");
    }

    @Override
    public boolean isSimpleValue() {
        throw new UnsupportedOperationException("Not a standalone node");
    }

    @Override
    public boolean split() {
        return false;
    }

    @Override
    public Segment toSegment() {
        return toSegmentWithJoinedChildren("ORDER BY ","", "", "", ", ", ", ");
    }
}
