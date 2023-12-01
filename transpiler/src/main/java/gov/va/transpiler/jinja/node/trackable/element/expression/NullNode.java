package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.Null;

import gov.va.transpiler.jinja.state.State;

public class NullNode extends ExpressionNode<Null> {

    public NullNode(State state, Null cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }
}
