package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.ParameterRef;

import gov.va.transpiler.jinja.state.State;

public class ParameterRefNode extends ExpressionNode<ParameterRef> {

    public ParameterRefNode(State state, ParameterRef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'name'", "'" + getCqlEquivalent().getName().replace(" ", "_") + "'");
        return map;
    }
    
}
