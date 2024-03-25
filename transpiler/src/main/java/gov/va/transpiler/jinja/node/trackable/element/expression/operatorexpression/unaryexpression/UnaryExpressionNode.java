package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.UnaryExpression;

import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.OperatorExpressionNode;
import gov.va.transpiler.jinja.state.State;

public class UnaryExpressionNode<T extends UnaryExpression> extends OperatorExpressionNode<T> {

    public UnaryExpressionNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }
}
