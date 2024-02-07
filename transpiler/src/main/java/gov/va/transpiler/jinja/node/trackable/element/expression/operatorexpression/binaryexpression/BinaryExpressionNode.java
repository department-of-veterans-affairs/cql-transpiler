package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.binaryexpression;

import org.hl7.elm.r1.BinaryExpression;

import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.OperatorExpressionNode;
import gov.va.transpiler.jinja.state.State;

public class BinaryExpressionNode<T extends BinaryExpression> extends OperatorExpressionNode<T> {

    public BinaryExpressionNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 2;
    }
}
