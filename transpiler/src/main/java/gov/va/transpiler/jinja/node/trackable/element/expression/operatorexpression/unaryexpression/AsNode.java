package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import java.util.Map;

import org.hl7.elm.r1.As;

import gov.va.transpiler.jinja.node.InvalidChildNodeException;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.typespecifier.TypeSpecifierNode;
import gov.va.transpiler.jinja.state.State;

public class AsNode extends UnaryExpressionNode<As> {

    private TranspilerNode typeSpecifierNode;

    public AsNode(State state, As cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof TypeSpecifierNode) {
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
