package gov.va.transpiler.jinja.node.ary.binary;

import org.hl7.elm.r1.After;

import gov.va.transpiler.jinja.state.State;

public class AfterNode extends BinaryOperatorNode<After> {

    public AfterNode(State state, After cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected String getOperator() {
        return ">";
    }
    
}
