package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.Exists;

import gov.va.transpiler.jinja.state.State;

public class ExistsNode extends UnaryExpressionNode<Exists> {

    public ExistsNode(State state, Exists cqlEquivalent) {
        super(state, cqlEquivalent);
    }    
}
