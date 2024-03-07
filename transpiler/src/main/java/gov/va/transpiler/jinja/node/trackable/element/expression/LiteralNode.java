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
        return 1;
    }

    @Override
    public Map<String, String> getSimpleArgumentMap() {
        var map = super.getSimpleArgumentMap();
        map.put("'type'", "'" + getCqlEquivalent().getValueType().getLocalPart() + "'");
        map.put("'value'", "'" + getCqlEquivalent().getValue() + "'");
        return map;
    }
}
