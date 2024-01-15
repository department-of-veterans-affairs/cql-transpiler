package gov.va.transpiler.jinja.node.other;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import org.hl7.elm.r1.Interval;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class IntervalNode extends CQLEquivalent<Interval> {

    private CQLEquivalent<?> low;
    private CQLEquivalent<?> lowClosed;
    private CQLEquivalent<?> high;
    private CQLEquivalent<?> highClosed;

    public IntervalNode(State state, Interval t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
        if (child instanceof CQLEquivalent<?>) {
            var childCast = (CQLEquivalent<?>) child;
            if (getCqlEquivalent().getLow() == childCast.getCqlEquivalent()) {
                low = childCast;
            } else if (getCqlEquivalent().getLowClosedExpression() == childCast.getCqlEquivalent()) {
                lowClosed = childCast;
            } else if (getCqlEquivalent().getHigh() == childCast.getCqlEquivalent()) {
                high = childCast;
            } else if (getCqlEquivalent().getHighClosedExpression() == childCast.getCqlEquivalent()) {
                highClosed = childCast;
            } else {
                throw new UnsupportedChildNodeException(this, child);
            }
        } else {
            throw new UnsupportedChildNodeException(this, child);
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

    private Segment elementToSegment(CQLEquivalent<?> node, String name) {
        var segment = new Segment();
        if (node == null) {
            segment.setHead("(SELECT NULL " + name + ")");
        } else {
            if (low.isSimpleValue()) {
                segment.setHead("(SELECT * FROM (");
                segment.addChild(node.toSegment());
                segment.setTail(") AS " + name + ")");
            } else {
                segment.setHead("(SELECT ");
                segment.addChild(node.toSegment());
                segment.setTail(" " + SINGLE_VALUE_COLUMN_NAME + " " + name + ")");
            }
        }
        return segment;
    }

    @Override
    public Segment toSegment() {
        var lowSegment = elementToSegment(low, "low");
        var lowClosedSegment = elementToSegment(lowClosed, "lowClosed");
        var highSegment = elementToSegment(high, "high");
        var highClosedSegment = elementToSegment(highClosed, "highClosed");

        var joinSegment = new Segment();
        joinSegment.setHead(") OUTER JOIN (");

        var segment = new Segment();
        segment.setHead("SELECT struct * AS " + SINGLE_VALUE_COLUMN_NAME + "FROM (");
        segment.addChild(lowSegment);
        segment.addChild(joinSegment);
        segment.addChild(lowClosedSegment);
        segment.addChild(joinSegment);
        segment.addChild(highSegment);
        segment.addChild(joinSegment);
        segment.addChild(highClosedSegment);
        return segment;
    }
}
