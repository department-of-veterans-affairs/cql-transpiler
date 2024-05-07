package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.ExpressionRef;

import gov.va.transpiler.jinja.state.State;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.ExpressionDefNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;

public class ExpressionRefNode<T extends ExpressionRef> extends ExpressionNode<T> implements ReferenceNode {

    final String prefix;

    public ExpressionRefNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
        prefix = state.getCurrentLibraryNode().getAliasForLibrary(state.getLibraryNodeForReference(getReferenceTo()));
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName().replace(' ', '_');
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
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'reference'", (prefix == null ? "" : prefix + ".") + ((ExpressionDefNode<?>) getReferenceTo()).getLibraryName() + referenceName());
        return map;
    }
}
