package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.Collections;
import java.util.List;
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
                if (highNode == null) {
                    highNode = child;
                } else {
                    super.addChild(child);
                }
            } else if (cqlEquivalentChild.getCqlEquivalent() == getCqlEquivalent().getHighClosedExpression()) {
                if (highClosedExpressionNode == null) {
                    highClosedExpressionNode = child;
                } else {
                    super.addChild(child);
                }
            } else if (cqlEquivalentChild.getCqlEquivalent() == getCqlEquivalent().getLow()) {
                if (lowNode == null) {
                    lowNode = child;
                } else {
                    super.addChild(child);
                }
            } else if (cqlEquivalentChild.getCqlEquivalent() == getCqlEquivalent().getHighClosedExpression()) {
                if (lowClosedExpressionNode == null) {
                    lowClosedExpressionNode = child;
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
    protected Map<String, List<TranspilerNode>> getNodeListArgumentMap() {
        var map = super.getNodeListArgumentMap();
        map.put("'high'", Collections.singletonList(highNode));
        map.put("'highClosedExpression'", highClosedExpressionNode == null ? Collections.emptyList() : Collections.singletonList(highClosedExpressionNode));
        map.put("'low'", Collections.singletonList(lowNode));
        map.put("'lowClosedExpression'", lowClosedExpressionNode == null ? Collections.emptyList() : Collections.singletonList(lowClosedExpressionNode));
        return map;
    }
}
