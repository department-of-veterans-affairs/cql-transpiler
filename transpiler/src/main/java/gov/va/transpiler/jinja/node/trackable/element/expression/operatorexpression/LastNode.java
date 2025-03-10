package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression;

import java.util.Map;

import org.hl7.elm.r1.Last;

import gov.va.transpiler.jinja.state.State;

public class LastNode extends OperatorExpressionNode<Last> {

    public LastNode(State state, Last cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        if (getCqlEquivalent().getOrderBy() != null) {
            // 'Last' Operators either include an orderBy parameter, or else are applied to ordered queries
            map.put("'orderBy'", getCqlEquivalent().getOrderBy());
        }
        return map;
    }
}
