package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression;

import java.util.Map;

import org.hl7.elm.r1.DateTime;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.state.State;

public class DateTimeNode extends OperatorExpressionNode<DateTime> {

    TranspilerNode yearNode;
    TranspilerNode monthNode;
    TranspilerNode dayNode;
    TranspilerNode hourNode;
    TranspilerNode minuteNode;
    TranspilerNode secondNode;
    TranspilerNode millisecondNode;
    TranspilerNode timezoneOffsetNode;

    public DateTimeNode(State state, DateTime cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof CQLEquivalent) {
            if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getYear()) {
                yearNode = child;
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getMonth()) {
                monthNode = child;
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getDay()) {
                dayNode = child;
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getHour()) {
                hourNode = child;
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getMinute()) {
                minuteNode = child;
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getSecond()) {
                secondNode = child;
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getMillisecond()) {
                millisecondNode = child;
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getTimezoneOffset()) {
                timezoneOffsetNode = child;
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
        map.put("'year'", yearNode);
        map.put("'month'", monthNode);
        map.put("'day'", dayNode);
        map.put("'hour'", hourNode);
        map.put("'minute'", minuteNode);
        map.put("'second'", secondNode);
        map.put("'millisecond'", millisecondNode);
        map.put("'timezoneOffset'", timezoneOffsetNode);
        return map;
    }
}
