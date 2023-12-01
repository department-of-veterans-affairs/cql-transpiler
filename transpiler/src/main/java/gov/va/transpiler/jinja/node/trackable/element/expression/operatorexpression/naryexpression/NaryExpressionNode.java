package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.naryexpression;

import org.hl7.elm.r1.NaryExpression;

import gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.OperatorExpressionNode;
import gov.va.transpiler.jinja.state.State;

public class NaryExpressionNode<T extends NaryExpression> extends OperatorExpressionNode<T> {

    public NaryExpressionNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
