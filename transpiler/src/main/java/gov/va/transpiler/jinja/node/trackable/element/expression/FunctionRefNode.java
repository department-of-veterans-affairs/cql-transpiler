package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.ExpressionDef;
import org.hl7.elm.r1.FunctionRef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.ExpressionDefNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.FunctionDefNode;
import gov.va.transpiler.jinja.state.State;

public class FunctionRefNode extends ExpressionRefNode<FunctionRef> {

    public FunctionRefNode(State state, FunctionRef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return TranspilerNode.UNLIMITED_CHILDREN;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ExpressionDefNode<ExpressionDef> referenceTo() {
        return (ExpressionDefNode<ExpressionDef>) referencedLibrary.getChildByNameAndType(referencedName(), FunctionDefNode.class);
    }
}
