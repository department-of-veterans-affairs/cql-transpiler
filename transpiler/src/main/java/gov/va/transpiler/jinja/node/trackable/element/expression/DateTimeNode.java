package gov.va.transpiler.jinja.node.trackable.element.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hl7.elm.r1.DateTime;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.state.State;

public class DateTimeNode extends ExpressionNode<DateTime> {

    List<TranspilerNode> yearList = new ArrayList<>();
    List<TranspilerNode> monthList = new ArrayList<>();
    List<TranspilerNode> dayList = new ArrayList<>();
    List<TranspilerNode> hourList = new ArrayList<>();
    List<TranspilerNode> minuteList = new ArrayList<>();
    List<TranspilerNode> secondList = new ArrayList<>();
    List<TranspilerNode> millisecondList = new ArrayList<>();
    List<TranspilerNode> timezoneOffsetList = new ArrayList<>();

    public DateTimeNode(State state, DateTime cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof CQLEquivalent) {
            if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getYear()) {
                yearList.add(child);
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getMonth()) {
                monthList.add(child);
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getDay()) {
                dayList.add(child);
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getHour()) {
                hourList.add(child);
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getMinute()) {
                minuteList.add(child);
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getSecond()) {
                secondList.add(child);
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getMillisecond()) {
                millisecondList.add(child);
            } else if (((CQLEquivalent<?>) child).getCqlEquivalent() == getCqlEquivalent().getTimezoneOffset()) {
                timezoneOffsetList.add(child);
            }
        } else {
            throw new UnsupportedChildNodeException(this, child);
        }
    }

    @Override
    protected Map<String, List<TranspilerNode>> getComplexArgumentMap() {
        var map = super.getComplexArgumentMap();
        map.put("'year'", yearList);
        map.put("'month'", monthList);
        map.put("'day'", dayList);
        map.put("'hour'", hourList);
        map.put("'minute'", minuteList);
        map.put("'second'", secondList);
        map.put("'millisecond'", millisecondList);
        map.put("'timezoneOffset'", timezoneOffsetList);
        return map;
    }
}
