package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.SingletonFrom;

import gov.va.transpiler.jinja.state.State;

public class SingletonFromNode extends UnaryExpressionNode<SingletonFrom> {

    public SingletonFromNode(State state, SingletonFrom cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
