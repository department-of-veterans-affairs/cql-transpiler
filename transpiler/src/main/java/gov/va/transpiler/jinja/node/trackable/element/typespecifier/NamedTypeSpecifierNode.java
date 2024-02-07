package gov.va.transpiler.jinja.node.trackable.element.typespecifier;

import org.hl7.elm.r1.NamedTypeSpecifier;

import gov.va.transpiler.jinja.state.State;

public class NamedTypeSpecifierNode extends TypeSpecifierNode<NamedTypeSpecifier> {

    public NamedTypeSpecifierNode(State state, NamedTypeSpecifier cqlEquivalent) {
        super(state, cqlEquivalent);
    }
    
}
