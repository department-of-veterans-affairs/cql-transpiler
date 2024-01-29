package gov.va.transpiler.sparkjinja.node.ary.binary;

import org.hl7.elm.r1.Multiply;

import gov.va.transpiler.sparkjinja.state.State;

public class MultiplyNode extends BinaryOperatorNode<Multiply> {

    public MultiplyNode(State state, Multiply cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected String getOperator() {
        return "*";
    }
}
