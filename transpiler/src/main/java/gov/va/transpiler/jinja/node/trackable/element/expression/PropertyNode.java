package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.Property;

import gov.va.transpiler.jinja.state.State;

public class PropertyNode extends ExpressionNode<Property> {

    public PropertyNode(State state, Property cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'scope'", getCqlEquivalent().getScope() == null ? "none" : "'" + getCqlEquivalent().getScope() + "'");
        map.put("'path'", getCqlEquivalent().getPath() == null ? "none" : "'" + getCqlEquivalent().getPath() + "'");
        return map;
    }
}
