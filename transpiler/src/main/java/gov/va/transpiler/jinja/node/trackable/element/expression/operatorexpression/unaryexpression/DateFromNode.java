package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.DateFrom;

import gov.va.transpiler.jinja.state.State;

public class DateFromNode extends UnaryExpressionNode<DateFrom> {

    public DateFromNode(State state, DateFrom cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
