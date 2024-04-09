package gov.va.transpiler.jinja.node.trackable.element.typespecifier;

import org.hl7.elm.r1.ListTypeSpecifier;

import gov.va.transpiler.jinja.state.State;

public class ListTypeSpecifierNode extends TypeSpecifierNode<ListTypeSpecifier> {

    public ListTypeSpecifierNode(State state, ListTypeSpecifier cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
