package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.Start;

import gov.va.transpiler.jinja.state.State;

public class StartNode extends UnaryExpressionNode<Start> {

    public StartNode(State state, Start cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
