package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import java.util.Map;

import org.hl7.elm.r1.DateTimeComponentFrom;

import gov.va.transpiler.jinja.state.State;

public class DateTimeComponentFromNode extends UnaryExpressionNode<DateTimeComponentFrom> {

    public DateTimeComponentFromNode(State state, DateTimeComponentFrom cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'precision'", "'" + getCqlEquivalent().getPrecision() + "'");
        return map;
    }
}
