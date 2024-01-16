package gov.va.transpiler.jinja.node.unary;

import org.hl7.elm.r1.ByExpression;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class ByExpressionNode extends Unary<ByExpression> {

    public ByExpressionNode(State state, ByExpression t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        return getChild().toSegment();
    }
}