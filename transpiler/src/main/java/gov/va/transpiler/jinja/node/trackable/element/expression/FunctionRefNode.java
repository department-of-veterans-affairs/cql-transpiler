package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.FunctionRef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.FunctionDefNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.state.State;

public class FunctionRefNode extends ExpressionNode<FunctionRef> implements ReferenceNode {

    final String prefix;

    public FunctionRefNode(State state, FunctionRef cqlEquivalent) {
        super(state, cqlEquivalent);
        prefix = state.getCurrentLibraryNode().getAliasForLibrary(state.getLibraryNodeForReference(getReferenceTo()));
    }

    @Override
    public String referenceName() {
        return getCqlEquivalent().getName();
    }

    @Override
    public String referenceType() {
        return FunctionDefNode.REFERENCE_TYPE;
    }

    @Override
    public TranspilerNode getChildByReference(String nameOrIndex) {
        return ((FunctionDefNode) getReferenceTo()).getChildByReference(nameOrIndex);
    }

    @Override
    protected Map<String, String> getSimpleArgumentMap() {
        var map = super.getSimpleArgumentMap();
        map.put("'reference'", (prefix == null ? "" : prefix + ".") + referenceName());
        return map;
    }
}
