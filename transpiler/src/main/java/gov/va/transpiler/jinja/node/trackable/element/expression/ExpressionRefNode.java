package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.ExpressionRef;

import gov.va.transpiler.jinja.state.State;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.ExpressionDefNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;

public class ExpressionRefNode extends ExpressionNode<ExpressionRef> implements ReferenceNode {

    public ExpressionRefNode(State state, ExpressionRef t) {
        super(state, t);
    }

    @Override
    public TranspilerNode getChildByReference(String nameOrIndex) {
        return ((ExpressionDefNode<ExpressionDef>) getReferenceTo()).getChildByReference(nameOrIndex);
    }

    @Override
    public Type getType() {
        return ((ExpressionDefNode<ExpressionDef>) getReferenceTo()).getType();
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
    public String getName() {
        return referenceName();
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }
}