package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.MaxValue;

import gov.va.transpiler.jinja.state.State;

public class MaxValueNode extends ExpressionNode<MaxValue> {

    public MaxValueNode(State state, MaxValue cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }
}
