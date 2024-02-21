package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.FunctionRef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.FunctionDefNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class FunctionRefNode extends ExpressionNode<FunctionRef> implements ReferenceNode {

    private String context;
    final String prefix;

    public FunctionRefNode(State state, FunctionRef cqlEquivalent) {
        super(state, cqlEquivalent);
        context = state.getContext();
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
    public Type getType() {
        return ((FunctionDefNode) getReferenceTo()).getType();
    }

    @Override
    public String getName() {
        return referenceName();
    }

    @Override
    public Segment childToSegment(TranspilerNode child) {
        if (child.getType() == Type.TABLE) {
            return childToSegmentCollectTable(context, child);
        } else if (child.getType() == Type.SIMPLE) {
            return childToSegmentEncapsulateSimple(child);
        } else {
            return super.childToSegment(child);
        }
    }

    @Override
    public Segment toSegment() {
        return joinChildren(getChildren(), (prefix == null ? "" : prefix + ".") + getName() + "(", ")", "", "", ", ");
    }
}
