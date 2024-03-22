package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.Retrieve;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.InvalidChildNodeException;
import gov.va.transpiler.jinja.state.State;

public class RetrieveNode extends ExpressionNode<Retrieve> {

    public List<TranspilerNode> valueSetRefList = new ArrayList<>();

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
            if (valueSetRefList.isEmpty()) {
                valueSetRefList.add(child);
            } else {
                throw new InvalidChildNodeException(this, child);
            }
        } else {
            super.addChild(child);
        }
    }

    @Override
    protected Map<String, String> getSimpleArgumentMap() {
        var map = super.getSimpleArgumentMap();
        map.put("'modelType'", "'" + getCqlEquivalent().getDataType().getNamespaceURI() + "'");
        map.put("'templateId'", getCqlEquivalent().getTemplateId() == null ? "none" : "'" + getCqlEquivalent().getTemplateId() + "'");
        map.put("'codeComparator'", getCqlEquivalent().getCodeComparator() == null ? "none" : "'" + getCqlEquivalent().getCodeComparator() + "'");
        map.put("'codeProperty'", getCqlEquivalent().getCodeProperty() == null ? "none" : "'" + getCqlEquivalent().getCodeProperty() + "'");
        return map;
    }

    @Override
    protected Map<String, List<TranspilerNode>> getComplexArgumentMap() {
        var map = super.getComplexArgumentMap();
        map.put("'valueSet'", valueSetRefList);
        return map;
    }

    // TODO
}
