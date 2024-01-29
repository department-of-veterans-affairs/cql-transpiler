package gov.va.transpiler.sparkjinja.node.unary;

import org.hl7.elm.r1.ByExpression;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

public class ByExpressionNode extends Unary<ByExpression> {

    public ByExpressionNode(State state, ByExpression t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        return getChild().toSegment();
    }
}