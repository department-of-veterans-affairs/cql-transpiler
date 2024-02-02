package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.ExpressionRef;

import gov.va.transpiler.jinja.state.State;
import gov.va.transpiler.jinja.node.trackable.element.ExpressionDefNode;
import gov.va.transpiler.jinja.node.utilityinterfaces.ReferenceNode;

public class ExpressionRefNode extends ExpressionNode<ExpressionRef> implements ReferenceNode {

    public ExpressionRefNode(State state, ExpressionRef t) {
        super(state, t);
    }

    @Override
    public Type getType() {
        return ((ExpressionDefNode) getReferenceTo()).getType();
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
