package gov.va.transpiler.jinja.node.trackable.element;

import java.util.Map;

import org.hl7.elm.r1.ByExpression;

import gov.va.transpiler.jinja.state.State;

public class ByExpressionNode extends SortByItemNode<ByExpression> {

    public ByExpressionNode(State state, ByExpression cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'direction'", getCqlEquivalent().getDirection() == null ? "none" : "'" + getCqlEquivalent().getDirection().name() + "'");
        return map;
    }
}
