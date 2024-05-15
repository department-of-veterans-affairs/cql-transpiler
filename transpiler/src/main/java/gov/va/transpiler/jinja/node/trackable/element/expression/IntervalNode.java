package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Map;

import org.hl7.elm.r1.Interval;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.state.State;

public class IntervalNode extends ExpressionNode<Interval> {

    TranspilerNode highNode;
    TranspilerNode highClosedExpressionNode;
    TranspilerNode lowNode;
    TranspilerNode lowClosedExpressionNode;

    public IntervalNode(State state, Interval cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    public void addChild(TranspilerNode child) {
        if (child instanceof CQLEquivalent) {
            var cqlEquivalentChild = (CQLEquivalent<?>) child;
            if (cqlEquivalentChild.getCqlEquivalent() == getCqlEquivalent().getHigh()) {
                highNode = child;
            } else if (cqlEquivalentChild.getCqlEquivalent() == getCqlEquivalent().getHighClosedExpression()) {
                highClosedExpressionNode = child;
            } else if (cqlEquivalentChild.getCqlEquivalent() == getCqlEquivalent().getLow()) {
                lowNode = child;
            } else if (cqlEquivalentChild.getCqlEquivalent() == getCqlEquivalent().getHighClosedExpression()) {
                lowClosedExpressionNode = child;
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
        map.put("'high'", highNode);
        map.put("'highClosedExpression'", highClosedExpressionNode);
        map.put("'low'", lowNode);
        map.put("'lowClosedExpression'", lowClosedExpressionNode);
        return map;
    }
}
