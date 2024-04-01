package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.Property;

import gov.va.transpiler.jinja.state.State;
import gov.va.transpiler.jinja.node.TranspilerNode;

public class PropertyNode extends ExpressionNode<Property> {

    public PropertyNode(State state, Property cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    public TranspilerNode getChildByReference(String nameOrIndex) {
        // expand chained property calls
        return getChild().getChildByReference(getCqlEquivalent().getPath()).getChildByReference(nameOrIndex);
    }

    @Override
    protected Map<String, String> getSimpleArgumentMap() {
        var map = super.getSimpleArgumentMap();
        map.put("'scope'", getCqlEquivalent().getScope() == null ? "none" : "'" + getCqlEquivalent().getScope() + "'");
        map.put("'path'", getCqlEquivalent().getPath() == null ? "none" : "'" + getCqlEquivalent().getPath() + "'");
        return map;
    }
}
