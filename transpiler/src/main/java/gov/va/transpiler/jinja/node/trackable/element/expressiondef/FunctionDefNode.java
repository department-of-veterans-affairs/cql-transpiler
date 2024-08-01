package gov.va.transpiler.jinja.node.trackable.element.expressiondef;

import java.util.Map;

import org.hl7.elm.r1.FunctionDef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.InvalidChildNodeException;
import gov.va.transpiler.jinja.node.trackable.element.OperandDefNode;
import gov.va.transpiler.jinja.state.State;

public class FunctionDefNode extends ExpressionDefNode<FunctionDef> {

    public TranspilerNode typeSpecifierNode;

    public FunctionDefNode(State state, FunctionDef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof OperandDefNode) {
            addNamedChild(((OperandDefNode) child).referenceName(), (OperandDefNode) child);
        } else if (child instanceof CQLEquivalent && ((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getResultTypeSpecifier()) {
            if (typeSpecifierNode == null) {
                typeSpecifierNode = child;
            } else {
                throw new InvalidChildNodeException(this, child);
            }
        } else {
            super.addChild(child);
        }
        processChildDependencies(child);
    }

    @Override
    protected Map<String, TranspilerNode> getNodeArgumentMap() {
        var map = super.getNodeArgumentMap();
        map.put("'typeSpecifier'", typeSpecifierNode);
        return map;
    }
}
