package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.Negate;

import gov.va.transpiler.jinja.state.State;

public class NegateNode extends UnaryExpressionNode<Negate> {

    public NegateNode(State state, Negate cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
