package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.If;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.state.State;

public class IfNode extends ExpressionNode<If> {

    TranspilerNode conditionNode;
    TranspilerNode thenNode;
    TranspilerNode elseNode;

    public IfNode(State state, If cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    public void addChild(TranspilerNode child) {
        if (child instanceof CQLEquivalent) {
            var cqlEquivalentChild = (CQLEquivalent<?>) child;
            if (cqlEquivalentChild.getCqlEquivalent() == getCqlEquivalent().getCondition()) {
                conditionNode = child;
            } else if (cqlEquivalentChild.getCqlEquivalent() == getCqlEquivalent().getThen()) {
                thenNode = child;
            } else if (cqlEquivalentChild.getCqlEquivalent() == getCqlEquivalent().getElse()) {
                elseNode = child;
            } else {
                super.addChild(child);
            }
        } else {
           super.addChild(child);
        }
        processChildDependencies(child);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }

    @Override
    protected Map<String, TranspilerNode> getNodeArgumentMap() {
        var map = super.getNodeArgumentMap();
        map.put("'condition'", conditionNode);
        map.put("'then'", thenNode);
        map.put("'else'", elseNode);
        return map;
    }
}
