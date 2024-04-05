package gov.va.transpiler.jinja.node.trackable.element;

import java.util.Map;

import org.hl7.elm.r1.OperandDef;

import gov.va.transpiler.jinja.state.State;

public class OperandDefNode extends ElementNode<OperandDef> {

    public OperandDefNode(State state, OperandDef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'name'",  "'" + getCqlEquivalent().getName() + "'");
        return map;
    }
}
