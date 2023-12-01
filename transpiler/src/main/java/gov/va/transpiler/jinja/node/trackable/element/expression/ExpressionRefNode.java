package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.ExpressionRef;

import gov.va.transpiler.jinja.state.State;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.IncludeDefNode;
import gov.va.transpiler.jinja.node.trackable.element.LibraryNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.ExpressionDefNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;

public class ExpressionRefNode<T extends ExpressionRef> extends ExpressionNode<T> implements ReferenceNode<ExpressionDefNode<ExpressionDef>> {

    protected final LibraryNode enclosingLibrary;
    protected final LibraryNode referencedLibrary;

    public ExpressionRefNode(State state, T cqlEquivalent) {
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
    public String referencedName() {
        return referencedLibrary.getCqlEquivalent().getIdentifier().getId() + getCqlEquivalent().getName();
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
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

    @SuppressWarnings("unchecked")
    @Override
    public ExpressionDefNode<ExpressionDef> referenceTo() {
        return (ExpressionDefNode<ExpressionDef>) referencedLibrary.getChildByNameAndType(referencedName(), ExpressionDefNode.class);
    }
}
