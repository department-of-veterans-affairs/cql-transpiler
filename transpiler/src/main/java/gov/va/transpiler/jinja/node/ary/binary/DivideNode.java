package gov.va.transpiler.jinja.node.ary.binary;

import org.hl7.elm.r1.Divide;

import gov.va.transpiler.jinja.state.State;

public class DivideNode extends BinaryOperatorNode<Divide> {

    public DivideNode(State state, Divide cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected String getOperator() {
        return "/";
    }
}
