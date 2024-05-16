package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.ExpressionRef;

import gov.va.transpiler.jinja.state.State;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.ExpressionDefNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;

public class ExpressionRefNode<T extends ExpressionRef> extends ExpressionNode<T> implements ReferenceNode {

    final boolean isReferenceToExternalLibrary;

    public ExpressionRefNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
        isReferenceToExternalLibrary = state.getCurrentLibraryNode().isReferenceToExternalLibrary(state.getLibraryNodeForReference(getReferenceTo()));
        if (isReferenceToExternalLibrary) {
            addMacroToMacroDependencies(state.getLibraryNodeForReference(((ExpressionDefNode<?>) getReferenceTo())).getTargetFileLocation(), ((ExpressionDefNode<?>) getReferenceTo()).getLibraryName() + referenceName());
        }
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
        map.put("'reference'", ((ExpressionDefNode<?>) getReferenceTo()).getLibraryName() + referenceName());
        return map;
    }
}
