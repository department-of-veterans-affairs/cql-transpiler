package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.IsNull;

import gov.va.transpiler.jinja.state.State;

public class IsNullNode extends UnaryExpressionNode<IsNull> {

    public IsNullNode(State state, IsNull cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
