package gov.va.transpiler.sparkjinja.node.ary.binary;

import org.hl7.elm.r1.Subtract;

import gov.va.transpiler.sparkjinja.state.State;

public class SubtractNode extends BinaryOperatorNode<Subtract> {

    public SubtractNode(State state, Subtract cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected String getOperator() {
        return "-";
    }
}
