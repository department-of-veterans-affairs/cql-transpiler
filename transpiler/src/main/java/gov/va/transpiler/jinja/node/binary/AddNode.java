package gov.va.transpiler.jinja.node.binary;

import org.hl7.elm.r1.Add;

import gov.va.transpiler.jinja.state.State;

public class AddNode extends BinaryOperatorNode<Add> {

    public AddNode(State state, Add cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected String getOperator() {
        return " +";
    }
}
