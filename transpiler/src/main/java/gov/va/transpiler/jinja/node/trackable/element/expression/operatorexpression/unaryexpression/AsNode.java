package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.As;

import gov.va.transpiler.jinja.node.InvalidChildNodeException;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.trackable.element.typespecifier.TypeSpecifierNode;
import gov.va.transpiler.jinja.state.State;

public class AsNode extends UnaryExpressionNode<As> {

    private TranspilerNode typeSpecifier;

    public AsNode(State state, As cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof TypeSpecifierNode) {
            if (typeSpecifier == null) {
                typeSpecifier = child;
            } else {
                throw new InvalidChildNodeException(this, child);
            }
        } else {
            super.addChild(child);
        }
    }

    @Override
    protected Map<String, List<TranspilerNode>> getNodeListArgumentMap() {
        var map = super.getNodeListArgumentMap();
        map.put("'typeSpecifier'", Collections.singletonList(typeSpecifier));
        return map;
    }
}
