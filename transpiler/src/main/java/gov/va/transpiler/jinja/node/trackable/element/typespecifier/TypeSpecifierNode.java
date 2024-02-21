package gov.va.transpiler.jinja.node.trackable.element.typespecifier;

import org.hl7.elm.r1.TypeSpecifier;

import gov.va.transpiler.jinja.node.trackable.element.ElementNode;
import gov.va.transpiler.jinja.state.State;

public class TypeSpecifierNode<T extends TypeSpecifier> extends ElementNode<T> {

    public TypeSpecifierNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
