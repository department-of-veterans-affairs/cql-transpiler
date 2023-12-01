package gov.va.transpiler.sparksql.node.ary;

import org.hl7.elm.r1.Interval;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Ary;
import gov.va.transpiler.sparksql.utilities.Standards;

public class IntervalNode extends Ary {

    private AbstractCQLNode low;
    private AbstractCQLNode lowClosed;
    private AbstractCQLNode high;
    private AbstractCQLNode highClosed;

    @Override
    public boolean isEncapsulated() {
        return true;
    }

    @Override
    public boolean addChild(AbstractCQLNode child) {
        Interval cqleq = (Interval) getCqlNodeEquivalent();
        if (cqleq.getLow() == child.getCqlNodeEquivalent()) {
            low = child;
            return true;
        } else if (cqleq.getLowClosedExpression() == child.getCqlNodeEquivalent()) {
            lowClosed = child;
            return true;
        } else if (cqleq.getHigh() == child.getCqlNodeEquivalent()) {
            high = child;
            return true;
        } else if (cqleq.getHighClosedExpression() == child.getCqlNodeEquivalent()) {
            highClosed = child;
            return true;
        }
        super.addChild(child);
        return false;
    }

    @Override
    public String asOneLine() {
        String lowAsString = "(SELECT NULL start)";
        String lowClosedAsString = "(SELECT NULL startClosed)";
        String highAsString = "(SELECT NULL end)";
        String highClosedAsString = "(SELECT NULL endClosed)";
        if (low != null) {
            lowAsString = low.isEncapsulated() ? "(SELECT * FROM (" + low.asOneLine() + ") AS start)" :
             "(SELECT " + low.asOneLine() + " " + Standards.SINGLE_VALUE_COLUMN_NAME + " start)";
        }
        if (lowClosed != null) {
            lowClosedAsString = lowClosed.isEncapsulated() ? "(SELECT * FROM  (" + lowClosed.asOneLine() + ") AS startClosed)" :
             "(SELECT " + lowClosed.asOneLine() + " " + Standards.SINGLE_VALUE_COLUMN_NAME + " startClosed)";
        }
        if (high != null) {
            highAsString = high.isEncapsulated() ? "(SELECT * FROM (" + high.asOneLine() + ") AS end)" :
             "(SELECT " + high.asOneLine() + " " + Standards.SINGLE_VALUE_COLUMN_NAME + " end)";
        }
        if (highClosed != null) {
            highClosedAsString = highClosed.isEncapsulated() ? "(SELECT * FROM (" + highClosed.asOneLine() + ") AS endClosed)" :
             "(SELECT " + highClosed.asOneLine() + " " + Standards.SINGLE_VALUE_COLUMN_NAME + " endClosed)";
        }
        return "SELECT struct(*) AS _val FROM (" + lowAsString + ") OUTER JOIN (" + lowClosedAsString + ") OUTER JOIN (" + highAsString + ") OUTER JOIN (" + highClosedAsString +")";
    }

    public AbstractCQLNode getLow() {
        return low;
    }

    public void setLow(AbstractCQLNode low) {
        this.low = low;
    }

    public AbstractCQLNode getLowClosed() {
        return lowClosed;
    }

    public void setLowClosed(AbstractCQLNode lowClosed) {
        this.lowClosed = lowClosed;
    }

    public AbstractCQLNode getHigh() {
        return high;
    }

    public void setHigh(AbstractCQLNode high) {
        this.high = high;
    }

    public AbstractCQLNode getHighClosed() {
        return highClosed;
    }

    public void setHighClosed(AbstractCQLNode highClosed) {
        this.highClosed = highClosed;
    }
}
