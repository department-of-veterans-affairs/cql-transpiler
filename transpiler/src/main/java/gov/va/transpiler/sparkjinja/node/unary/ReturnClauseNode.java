package gov.va.transpiler.sparkjinja.node.unary;

import org.hl7.elm.r1.ReturnClause;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

public class ReturnClauseNode extends Unary<ReturnClause> {

    public ReturnClauseNode(State state, ReturnClause t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        return getChild().toSegment();
    }
}
