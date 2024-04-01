package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Collections;
import java.util.List;
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
                if (conditionNode == null) {
                    conditionNode = child;
                } else {
                    super.addChild(child);
                }
            } else if (cqlEquivalentChild.getCqlEquivalent() == getCqlEquivalent().getThen()) {
                if (thenNode == null) {
                    thenNode = child;
                } else {
                    super.addChild(child);
                }
            } else if (cqlEquivalentChild.getCqlEquivalent() == getCqlEquivalent().getElse()) {
                if (elseNode == null) {
                    elseNode = child;
                } else {
                    super.addChild(child);
                }
            } else {
                super.addChild(child);
            }
        } else {
           super.addChild(child);
        }
    }

    @Override
    public int allowedNumberOfChildren() {
        return 0;
    }

    @Override
    protected Map<String, List<TranspilerNode>> getComplexArgumentMap() {
        var map = super.getComplexArgumentMap();
        map.put("'condition'", Collections.singletonList(conditionNode));
        map.put("'then'", Collections.singletonList(thenNode));
        map.put("'else'", Collections.singletonList(elseNode));
        return map;
    }
}
