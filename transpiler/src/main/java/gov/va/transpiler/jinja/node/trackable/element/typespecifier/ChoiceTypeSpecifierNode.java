package gov.va.transpiler.jinja.node.trackable.element.typespecifier;

import org.hl7.elm.r1.ChoiceTypeSpecifier;

import gov.va.transpiler.jinja.state.State;

public class ChoiceTypeSpecifierNode extends TypeSpecifierNode<ChoiceTypeSpecifier> {

    public ChoiceTypeSpecifierNode(State state, ChoiceTypeSpecifier cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
