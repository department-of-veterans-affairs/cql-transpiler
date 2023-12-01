package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.Literal;

import gov.va.transpiler.jinja.state.State;

public class LiteralNode extends ExpressionNode<Literal> {

    public enum LiteralType {
        Unsupported,
        Integer,
        String
    }

    public LiteralNode(State state, Literal t) {
        super(state, t);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }

    @Override
    public Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'type'", "'" + getCqlEquivalent().getValueType().getLocalPart() + "'");
        map.put("'value'", "'" + getCqlEquivalent().getValue() + "'");
        return map;
    }
}
