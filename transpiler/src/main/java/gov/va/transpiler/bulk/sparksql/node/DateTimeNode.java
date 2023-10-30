package gov.va.transpiler.bulk.sparksql.node;

import static gov.va.transpiler.bulk.sparksql.utilities.Standards.DEFAULT_SQL_DATE_TIME;

import gov.va.transpiler.node.OutputNode;

public class DateTimeNode extends AbstractNodeNoChildren {

    private OutputNode year;
    private OutputNode month;
    private OutputNode day;
    private OutputNode hour;
    private OutputNode minute;
    private OutputNode second;
    private OutputNode millisecond;
    private OutputNode timezoneOffset;

    public OutputNode getYear() {
        return year;
    }

    public void setYear(OutputNode year) {
        this.year = year;
    }

    public OutputNode getMonth() {
        return month;
    }

    public void setMonth(OutputNode month) {
        this.month = month;
    }

    public OutputNode getDay() {
        return day;
    }

    public void setDay(OutputNode day) {
        this.day = day;
    }

    public OutputNode getHour() {
        return hour;
    }

    public void setHour(OutputNode hour) {
        this.hour = hour;
    }

    public OutputNode getMinute() {
        return minute;
    }

    public void setMinute(OutputNode minute) {
        this.minute = minute;
    }

    public OutputNode getSecond() {
        return second;
    }

    public void setSecond(OutputNode second) {
        this.second = second;
    }

    public OutputNode getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(OutputNode millisecond) {
        this.millisecond = millisecond;
    }

    public OutputNode getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(OutputNode timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    @Override
    public String asOneLine() {
        // DateTimes are a MASSIVE can of worms. I'm just not going to worry about them for now.
        return DEFAULT_SQL_DATE_TIME;
    }
}
