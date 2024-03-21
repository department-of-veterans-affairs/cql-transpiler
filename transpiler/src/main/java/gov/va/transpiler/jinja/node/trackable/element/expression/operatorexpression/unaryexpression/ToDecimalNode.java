package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.ToDecimal;

import gov.va.transpiler.jinja.state.State;

public class ToDecimalNode extends UnaryExpressionNode<ToDecimal> {

    public ToDecimalNode(State state, ToDecimal cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
