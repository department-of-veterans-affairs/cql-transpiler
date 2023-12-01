package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.MinValue;

import gov.va.transpiler.jinja.state.State;

public class MinValueNode extends ExpressionNode<MinValue> {

    public MinValueNode(State state, MinValue cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }
}
