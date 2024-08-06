package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.ValueSetRef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.IncludeDefNode;
import gov.va.transpiler.jinja.node.trackable.element.LibraryNode;
import gov.va.transpiler.jinja.node.trackable.element.ValueSetDefNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.state.State;

public class ValueSetRefNode extends ExpressionNode<ValueSetRef> implements ReferenceNode<ValueSetDefNode> {

    private final LibraryNode enclosingLibrary;
    private final LibraryNode referencedLibrary;

    public ValueSetRefNode(State state, ValueSetRef cqlEquivalent) {
        super(state, cqlEquivalent);
        enclosingLibrary = state.getCurrentLibrary();
        if (getCqlEquivalent().getLibraryName() == null) {
            referencedLibrary = enclosingLibrary;
        } else {
            // if this ValueSetRef has a library name set, it refers to an external library
            var includeDefNode = (IncludeDefNode) enclosingLibrary.getChildByNameAndType(getCqlEquivalent().getLibraryName(), IncludeDefNode.class);
            referencedLibrary = (LibraryNode) includeDefNode.referenceTo();
        }
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }

    @Override
    public String referencedName() {
        return referencedLibrary.referenceName() + getCqlEquivalent().getName();
    }

    @Override
    protected Map<String, String> getLiteralArgumentMap() {
        var map = super.getLiteralArgumentMap();
        map.put("'referencedName'", "'" + sanitizeNameForJinja(referencedName()) + "'");
        return map;
    }

    @Override
    protected Map<String, TranspilerNode> getNodeArgumentMap() {
        var map = super.getNodeArgumentMap();
        map.put("'referenceTo'", referenceTo());
        return map;
    }

    @Override
    public ValueSetDefNode referenceTo() {
        return (ValueSetDefNode) referencedLibrary.getChildByNameAndType(referencedName(), ValueSetDefNode.class);
    }
}
