package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression;

import java.util.Map;

import org.hl7.elm.r1.First;

import gov.va.transpiler.jinja.state.State;

public class FirstNode extends OperatorExpressionNode<First> {

    public FirstNode(State state, First cqlEquivalent) {
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
            map.put("'orderBy'", getCqlEquivalent().getOrderBy());
        }
        return map;
    }
}
