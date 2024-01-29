package gov.va.transpiler.sparkjinja.node.ary.binary;

import org.hl7.elm.r1.Before;

import gov.va.transpiler.sparkjinja.state.State;

public class BeforeNode extends BinaryOperatorNode<Before> {

    public BeforeNode(State state, Before cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected String getOperator() {
        return "<";
    }
    
}
