package gov.va.transpiler.jinja.node.trackable.element;

import java.util.Map;

import org.hl7.elm.r1.OperandDef;

import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceableNode;
import gov.va.transpiler.jinja.state.State;

public class OperandDefNode extends ElementNode<OperandDef> implements ReferenceableNode {

    public OperandDefNode(State state, OperandDef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'referenceName'",  "'" + referenceName() + "'");
        return map;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName();
    }
}
