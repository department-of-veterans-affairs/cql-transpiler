package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression;

import org.hl7.elm.r1.OperatorExpression;

import gov.va.transpiler.jinja.node.trackable.element.expression.ExpressionNode;
import gov.va.transpiler.jinja.state.State;

public abstract class OperatorExpressionNode<T extends OperatorExpression> extends ExpressionNode<T> {

    public OperatorExpressionNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }    
}
