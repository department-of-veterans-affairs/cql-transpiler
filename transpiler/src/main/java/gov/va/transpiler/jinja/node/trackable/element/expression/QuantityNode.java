package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.Quantity;

import gov.va.transpiler.jinja.state.State;

public class QuantityNode extends ExpressionNode<Quantity> {

    public QuantityNode(State state, Quantity cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'unit'", "'" + getCqlEquivalent().getUnit() + "'");
        map.put("'value'", "'" + getCqlEquivalent().getValue() + "'");
        return map;
    }
}
