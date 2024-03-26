package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.ToDate;

import gov.va.transpiler.jinja.state.State;

public class ToDateNode extends UnaryExpressionNode<ToDate> {

    public ToDateNode(State state, ToDate cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
