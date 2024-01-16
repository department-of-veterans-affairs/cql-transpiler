package gov.va.transpiler.jinja.node.ary.binary;

import org.hl7.elm.r1.Equal;

import gov.va.transpiler.jinja.state.State;

public class EqualNode extends BinaryOperatorNode<Equal> {

    public EqualNode(State state, Equal cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected String getOperator() {
        return "=";
    }
}
