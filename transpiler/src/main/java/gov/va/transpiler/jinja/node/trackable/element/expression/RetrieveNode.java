package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.cql.model.ClassType;
import org.hl7.cql.model.ListType;
import org.hl7.elm.r1.Retrieve;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.InvalidChildNodeException;
import gov.va.transpiler.jinja.state.State;

public class RetrieveNode extends ExpressionNode<Retrieve> {

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
        processChildDependencies(child);
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'modelType'", "'" + getCqlEquivalent().getDataType().getNamespaceURI() + "'");
        map.put("'templateId'", getCqlEquivalent().getTemplateId() == null ? "none" : "'" + getCqlEquivalent().getTemplateId() + "'");
        // Some models have a label that's different from their template ID
        var resultTypeLabel = "none";
        if (getCqlEquivalent().getResultType() instanceof ListType) {
            var resultType = (ListType) getCqlEquivalent().getResultType();
            if (resultType.getElementType() instanceof ClassType) {
                var elementType = (ClassType) resultType.getElementType();
                if (elementType.getLabel() != null) {
                    resultTypeLabel = "'" + elementType.getLabel() + "'";
                }
            }
        }
        map.put("'resultTypeLabel'", resultTypeLabel);
        map.put("'codeComparator'", getCqlEquivalent().getCodeComparator() == null ? "none" : "'" + getCqlEquivalent().getCodeComparator() + "'");
        map.put("'codeProperty'", getCqlEquivalent().getCodeProperty() == null ? "none" : "'" + getCqlEquivalent().getCodeProperty() + "'");
        map.put("'codeSearch'", getCqlEquivalent().getCodeSearch() == null ? "none" : "'" + getCqlEquivalent().getCodeSearch() + "'");
        map.put("'valueSetProperty'", getCqlEquivalent().getValueSetProperty() == null ? "none" : "'" + getCqlEquivalent().getValueSetProperty() + "'");
        return map;
    }

    @Override
    protected Map<String, TranspilerNode> getNodeArgumentMap() {
        var map = super.getNodeArgumentMap();
        map.put("'valueSet'", valueSetNode);
        return map;
    }
}
