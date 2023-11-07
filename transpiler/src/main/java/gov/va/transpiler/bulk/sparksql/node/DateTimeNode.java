package gov.va.transpiler.bulk.sparksql.node;

import static gov.va.transpiler.bulk.sparksql.utilities.Standards.DEFAULT_SQL_DATE_TIME;

import org.cqframework.cql.elm.tracking.Trackable;
import org.hl7.elm.r1.DateTime;

import gov.va.transpiler.node.OutputNode;

public class DateTimeNode extends AbstractNodeNoChildren<DateTime> {

    private OutputNode<? extends Trackable> year;
    private OutputNode<? extends Trackable> month;
    private OutputNode<? extends Trackable> day;
    private OutputNode<? extends Trackable> hour;
    private OutputNode<? extends Trackable> minute;
    private OutputNode<? extends Trackable> second;
    private OutputNode<? extends Trackable> millisecond;
    private OutputNode<? extends Trackable> timezoneOffset;

    public OutputNode<? extends Trackable> getYear() {
        return year;
    }

    public void setYear(OutputNode<? extends Trackable> year) {
        this.year = year;
    }

    public OutputNode<? extends Trackable> getMonth() {
        return month;
    }

    public void setMonth(OutputNode<? extends Trackable> month) {
        this.month = month;
    }

    public OutputNode<? extends Trackable> getDay() {
        return day;
    }

    public void setDay(OutputNode<? extends Trackable> day) {
        this.day = day;
    }

    public OutputNode<? extends Trackable> getHour() {
        return hour;
    }

    public void setHour(OutputNode<? extends Trackable> hour) {
        this.hour = hour;
    }

    public OutputNode<? extends Trackable> getMinute() {
        return minute;
    }

    public void setMinute(OutputNode<? extends Trackable> minute) {
        this.minute = minute;
    }

    public OutputNode<? extends Trackable> getSecond() {
        return second;
    }

    public void setSecond(OutputNode<? extends Trackable> second) {
        this.second = second;
    }

    public OutputNode<? extends Trackable> getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(OutputNode<? extends Trackable> millisecond) {
        this.millisecond = millisecond;
    }

    public OutputNode<? extends Trackable> getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(OutputNode<? extends Trackable> timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    @Override
    public String asOneLine() {
        // DateTimes are a MASSIVE can of worms. I'm just not going to worry about them for now.
        return DEFAULT_SQL_DATE_TIME;
    }
}
