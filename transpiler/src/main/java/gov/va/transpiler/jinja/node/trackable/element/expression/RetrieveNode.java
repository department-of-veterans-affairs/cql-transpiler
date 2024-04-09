package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.Retrieve;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.InvalidChildNodeException;
import gov.va.transpiler.jinja.state.State;

public class RetrieveNode extends ExpressionNode<Retrieve> {
    // TODO: arguments included in the original CQL node are missing from the list of simple arguments

    public TranspilerNode valueSetNode;

    public RetrieveNode(State state, Retrieve cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    public void addChild(TranspilerNode child) {
        if(child instanceof ValueSetRefNode) {
            if (valueSetNode == null) {
                valueSetNode = child;
            } else {
                throw new InvalidChildNodeException(this, child);
            }
        } else {
            super.addChild(child);
        }
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'modelType'", "'" + getCqlEquivalent().getDataType().getNamespaceURI() + "'");
        map.put("'templateId'", getCqlEquivalent().getTemplateId() == null ? "none" : "'" + getCqlEquivalent().getTemplateId() + "'");
        map.put("'codeComparator'", getCqlEquivalent().getCodeComparator() == null ? "none" : "'" + getCqlEquivalent().getCodeComparator() + "'");
        map.put("'codeProperty'", getCqlEquivalent().getCodeProperty() == null ? "none" : "'" + getCqlEquivalent().getCodeProperty() + "'");
        return map;
    }

    @Override
    protected Map<String, TranspilerNode> getNodeArgumentMap() {
        var map = super.getNodeArgumentMap();
        map.put("'valueSet'", valueSetNode);
        return map;
    }
}
