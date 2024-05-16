package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.ValueSetRef;

import gov.va.transpiler.jinja.node.trackable.element.ValueSetDefNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.state.State;

public class ValueSetRefNode extends ExpressionNode<ValueSetRef> implements ReferenceNode {

    final boolean isReferenceToExternalLibrary;

    public ValueSetRefNode(State state, ValueSetRef cqlEquivalent) {
        super(state, cqlEquivalent);
        isReferenceToExternalLibrary = state.getCurrentLibraryNode().isReferenceToExternalLibrary(state.getLibraryNodeForReference(getReferenceTo()));
        if (isReferenceToExternalLibrary) { addMacroToMacroDependencies(state.getLibraryNodeForReference(((ValueSetDefNode) getReferenceTo())).getTargetFileLocation(), ((ValueSetDefNode) getReferenceTo()).getLibraryName() + referenceName());
        }
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName().replace(' ', '_');
    }

    @Override
    public String referenceType() {
        return ValueSetDefNode.REFERENCE_TYPE;
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'reference'", ((ValueSetDefNode) getReferenceTo()).getLibraryName() + referenceName());
        return map;
    }
}
