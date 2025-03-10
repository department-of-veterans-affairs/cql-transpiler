package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression;

import java.util.Map;

import org.hl7.elm.r1.InValueSet;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.InvalidChildNodeException;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.state.State;

public class InValueSetNode extends OperatorExpressionNode<InValueSet> {

    private TranspilerNode valueSetReferenceNode;
    private TranspilerNode valueSetExpressionNode;

    public InValueSetNode(State state, InValueSet cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof CQLEquivalent) {
            if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getValueset()) {
                valueSetReferenceNode = child;
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getValuesetExpression()){
                valueSetExpressionNode = child;
            } else {
                super.addChild(child);
            }
        } else {
            throw new InvalidChildNodeException(this, child);
        }
        processChildDependencies(child);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    protected Map<String, TranspilerNode> getNodeArgumentMap() {
        var map = super.getNodeArgumentMap();
        map.put("'valueSetReference'", valueSetReferenceNode);
        map.put("'valueSetExpression'", valueSetExpressionNode);
        return map;
    }
}
