package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.ExpressionRef;

import gov.va.transpiler.jinja.state.State;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.ExpressionDefNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;

public class ExpressionRefNode extends ExpressionNode<ExpressionRef> implements ReferenceNode {

    final String prefix;

    public ExpressionRefNode(State state, ExpressionRef t) {
        super(state, t);
        prefix = state.getCurrentLibraryNode().getAliasForLibrary(state.getLibraryNodeForReference(getReferenceTo()));
    }

    @Override
    public TranspilerNode getChildByReference(String nameOrIndex) {
        return ((ExpressionDefNode<?>) getReferenceTo()).getChildByReference(nameOrIndex);
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName();
    }

    @Override
    public String referenceType() {
        return ExpressionDefNode.REFERENCE_TYPE;
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }

    @Override
    protected Map<String, String> getSimpleArgumentMap() {
        var map = super.getSimpleArgumentMap();
        map.put("'reference'", (prefix == null ? "" : prefix + ".") + referenceName());
        return map;
    }
}
