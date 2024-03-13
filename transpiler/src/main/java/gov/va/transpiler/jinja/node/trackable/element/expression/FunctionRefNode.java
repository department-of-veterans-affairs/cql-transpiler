package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.FunctionRef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.FunctionDefNode;
import gov.va.transpiler.jinja.state.State;

public class FunctionRefNode extends ExpressionRefNode<FunctionRef> {


    public FunctionRefNode(State state, FunctionRef cqlEquivalent) {
        super(state, cqlEquivalent);
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
    public int allowedNumberOfChildren() {
        return TranspilerNode.UNLIMITED_CHILDREN;
    }
}
