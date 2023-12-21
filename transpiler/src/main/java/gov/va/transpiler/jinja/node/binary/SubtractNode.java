package gov.va.transpiler.jinja.node.binary;

import org.hl7.elm.r1.Subtract;

import gov.va.transpiler.jinja.state.State;

public class SubtractNode extends BinaryOperatorNode<Subtract> {

    public SubtractNode(State state, Subtract cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected String getOperator() {
        return " -";
    }
}
