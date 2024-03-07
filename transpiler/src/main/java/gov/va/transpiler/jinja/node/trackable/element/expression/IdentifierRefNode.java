package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.IdentifierRef;

import gov.va.transpiler.jinja.state.State;

public class IdentifierRefNode extends ExpressionNode<IdentifierRef> {

    public IdentifierRefNode(State state, IdentifierRef cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
