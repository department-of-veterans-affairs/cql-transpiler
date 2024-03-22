package gov.va.transpiler.jinja.node.trackable.element.expressiondef;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.FunctionDef;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.InvalidChildNodeException;
import gov.va.transpiler.jinja.node.trackable.element.OperandDefNode;
import gov.va.transpiler.jinja.node.trackable.element.typespecifier.TypeSpecifierNode;
import gov.va.transpiler.jinja.state.State;

public class FunctionDefNode extends ExpressionDefNode<FunctionDef> {

    public static final String REFERENCE_TYPE = "FunctionDef";

    public List<TranspilerNode> operandDefNodeList = new ArrayList<>();
    public TypeSpecifierNode<?> typeSpecifierNode;

    public FunctionDefNode(State state, FunctionDef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof OperandDefNode) {
            operandDefNodeList.add((OperandDefNode) child);
        } else if (child instanceof TypeSpecifierNode) {
            if (typeSpecifierNode == null) {
                typeSpecifierNode = (TypeSpecifierNode<?>) child;
            } else {
                throw new InvalidChildNodeException(this, child);
            }
        } else {
            super.addChild(child);
        }
    }

    @Override
    protected Map<String, List<TranspilerNode>> getComplexArgumentMap() {
        var map = super.getComplexArgumentMap();
        map.put("'operators'", operandDefNodeList);
        return map;
    }

    @Override
    public String referenceType() {
        return REFERENCE_TYPE;
    }
}
