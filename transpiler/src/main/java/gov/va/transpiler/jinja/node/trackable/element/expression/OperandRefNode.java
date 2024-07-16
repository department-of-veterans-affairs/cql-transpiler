package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.OperandRef;

import gov.va.transpiler.jinja.state.State;

public class OperandRefNode extends ExpressionNode<OperandRef> {

    public OperandRefNode(State state, OperandRef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'reference'",  "'" + getCqlEquivalent().getName() + "'");
        return map;
    }
}
