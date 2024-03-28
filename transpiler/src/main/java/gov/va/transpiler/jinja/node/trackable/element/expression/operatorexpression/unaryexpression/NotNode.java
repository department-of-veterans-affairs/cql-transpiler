package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.Not;

import gov.va.transpiler.jinja.state.State;

public class NotNode extends UnaryExpressionNode<Not> {

    public NotNode(State state, Not cqlEquivalent) {
        super(state, cqlEquivalent);
    }    
}
