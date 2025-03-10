package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.ternaryexpression;

import org.hl7.elm.r1.TernaryExpression;

import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.OperatorExpressionNode;
import gov.va.transpiler.jinja.state.State;

public abstract class TernaryExpressionNode<T extends TernaryExpression> extends OperatorExpressionNode<T> {

    public TernaryExpressionNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 3;
    }
}
