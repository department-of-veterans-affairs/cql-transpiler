package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.IdentifierRef;

import gov.va.transpiler.jinja.state.State;

public class IdentifierRefNode extends ExpressionNode<IdentifierRef> {
// TODO: include reference source as nested operator tree inside output

    public IdentifierRefNode(State state, IdentifierRef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'referencedName'", "'" + getCqlEquivalent().getName() + "'");
        return map;
    }
}
