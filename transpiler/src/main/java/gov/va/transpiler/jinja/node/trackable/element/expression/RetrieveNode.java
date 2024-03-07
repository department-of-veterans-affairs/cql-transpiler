package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.Retrieve;

import gov.va.transpiler.jinja.state.State;

public class RetrieveNode extends ExpressionNode<Retrieve> {

    public RetrieveNode(State state, Retrieve cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 2;
    }

    @Override
    protected Map<String, String> getSimpleArgumentMap() {
        var map = super.getSimpleArgumentMap();
        map.put("'modelType'", "'" + getCqlEquivalent().getDataType().getNamespaceURI() + "'");
        map.put("'valueSet'", getCqlEquivalent().getValueSetProperty() == null ? "none" : "'" + getCqlEquivalent().getValueSetProperty() + "'");
        map.put("'templateId'", "'" + getCqlEquivalent().getTemplateId() + "'");
        return map;
    }
}
