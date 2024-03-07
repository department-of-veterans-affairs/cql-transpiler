package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.Flatten;

import gov.va.transpiler.jinja.state.State;

public class FlattenNode extends UnaryExpressionNode<Flatten> {

    public FlattenNode(State state, Flatten cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
