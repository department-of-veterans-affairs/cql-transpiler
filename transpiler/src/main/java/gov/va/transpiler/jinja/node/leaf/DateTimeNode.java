package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.DateTime;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class DateTimeNode extends Leaf<DateTime> {

    private TranspilerNode year;
    private TranspilerNode month;
    private TranspilerNode day;
    private TranspilerNode hour;
    private TranspilerNode minute;
    private TranspilerNode second;
    private TranspilerNode millisecond;
    private TranspilerNode timezoneOffset;

    public DateTimeNode(State state, DateTime t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (child instanceof CQLEquivalent<?>) {
            CQLEquivalent<?> equivalent = (CQLEquivalent<?>) child;
            if (equivalent.getCqlEquivalent() == getCqlEquivalent().getTimezoneOffset()) {
                setTimezoneOffset(equivalent);
            } else if (equivalent.getCqlEquivalent() == getCqlEquivalent().getMillisecond()) {
                setMillisecond(equivalent);
            } else if (equivalent.getCqlEquivalent() == getCqlEquivalent().getSecond()) {
                setSecond(child);
            } else if (equivalent.getCqlEquivalent() == getCqlEquivalent().getMinute()) {
                setMinute(equivalent);
            } else if (equivalent.getCqlEquivalent() == getCqlEquivalent().getHour()) {
                setHour(equivalent);
            } else if (equivalent.getCqlEquivalent() == getCqlEquivalent().getDay()) {
                setDay(equivalent);
            } else if (equivalent.getCqlEquivalent() == getCqlEquivalent().getMonth()) {
                setMonth(equivalent);
            } else if (equivalent.getCqlEquivalent() == getCqlEquivalent().getYear()) {
                setYear(equivalent);
            } else {
                super.addChild(child);
            }
        } else {
            super.addChild(child);
        }
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public boolean isSimpleValue() {
        return true;
    }

    @Override
    public Segment toSegment() {
        // TODO: support for date formats
        var segment = new Segment();
        segment.setHead("?INSERT DATE HERE?");
        return segment;
    }

    public TranspilerNode getYear() {
        return year;
    }

    public void setYear(TranspilerNode year) {
        this.year = year;
    }

    public TranspilerNode getMonth() {
        return month;
    }

    public void setMonth(TranspilerNode month) {
        this.month = month;
    }

    public TranspilerNode getDay() {
        return day;
    }

    public void setDay(TranspilerNode day) {
        this.day = day;
    }

    public TranspilerNode getHour() {
        return hour;
    }

    public void setHour(TranspilerNode hour) {
        this.hour = hour;
    }

    public TranspilerNode getMinute() {
        return minute;
    }

    public void setMinute(TranspilerNode minute) {
        this.minute = minute;
    }

    public TranspilerNode getSecond() {
        return second;
    }

    public void setSecond(TranspilerNode second) {
        this.second = second;
    }

    public TranspilerNode getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(TranspilerNode millisecond) {
        this.millisecond = millisecond;
    }

    public TranspilerNode getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(TranspilerNode timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }
}
