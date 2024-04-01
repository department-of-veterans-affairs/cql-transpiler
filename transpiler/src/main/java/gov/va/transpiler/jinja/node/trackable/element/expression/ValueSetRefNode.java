package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.ValueSetRef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.ValueSetDefNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.state.State;

public class ValueSetRefNode extends ExpressionNode<ValueSetRef> implements ReferenceNode {

    final String prefix;

    public ValueSetRefNode(State state, ValueSetRef cqlEquivalent) {
        super(state, cqlEquivalent);
        prefix = state.getCurrentLibraryNode().getAliasForLibrary(state.getLibraryNodeForReference(getReferenceTo()));
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }

    @Override
    public TranspilerNode getChildByReference(String nameOrIndex) {
        return ((ValueSetDefNode) getReferenceTo()).getChildByReference(nameOrIndex);
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
    protected Map<String, String> getSimpleArgumentMap() {
        var map = super.getSimpleArgumentMap();
        map.put("'reference'", (prefix == null ? "" : prefix + ".") + referenceName());
        return map;
    }
}
