package gov.va.transpiler.sparksql.node.leaf;

import static gov.va.transpiler.sparksql.utilities.Standards.DEFAULT_SQL_DATE_TIME;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Leaf;

public class DateTimeNode extends Leaf {

    private AbstractCQLNode year;
    private AbstractCQLNode month;
    private AbstractCQLNode day;
    private AbstractCQLNode hour;
    private AbstractCQLNode minute;
    private AbstractCQLNode second;
    private AbstractCQLNode millisecond;
    private AbstractCQLNode timezoneOffset;

    public AbstractCQLNode getYear() {
        return year;
    }

    public void setYear(AbstractCQLNode year) {
        this.year = year;
    }

    public AbstractCQLNode getMonth() {
        return month;
    }

    public void setMonth(AbstractCQLNode month) {
        this.month = month;
    }

    public AbstractCQLNode getDay() {
        return day;
    }

    public void setDay(AbstractCQLNode day) {
        this.day = day;
    }

    public AbstractCQLNode getHour() {
        return hour;
    }

    public void setHour(AbstractCQLNode hour) {
        this.hour = hour;
    }

    public AbstractCQLNode getMinute() {
        return minute;
    }

    public void setMinute(AbstractCQLNode minute) {
        this.minute = minute;
    }

    public AbstractCQLNode getSecond() {
        return second;
    }

    public void setSecond(AbstractCQLNode second) {
        this.second = second;
    }

    public AbstractCQLNode getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(AbstractCQLNode millisecond) {
        this.millisecond = millisecond;
    }

    public AbstractCQLNode getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(AbstractCQLNode timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    @Override
    public String asOneLine() {
        // DateTimes are a MASSIVE can of worms. I'm just not going to worry about them for now.
        return DEFAULT_SQL_DATE_TIME;
    }
}
