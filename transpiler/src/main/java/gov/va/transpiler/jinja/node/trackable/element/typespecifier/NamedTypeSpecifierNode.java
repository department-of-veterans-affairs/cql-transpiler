package gov.va.transpiler.jinja.node.trackable.element.typespecifier;

import java.util.Map;

import org.hl7.elm.r1.NamedTypeSpecifier;

import gov.va.transpiler.jinja.state.State;

public class NamedTypeSpecifierNode extends TypeSpecifierNode<NamedTypeSpecifier> {

    public NamedTypeSpecifierNode(State state, NamedTypeSpecifier cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'name'",  "'" + getCqlEquivalent().getName().toString() + "'");
        return map;
    }
}
