package gov.va.transpiler.jinja.node.ary;

import org.hl7.elm.r1.List;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class ListNode extends Ary<List> {

    public ListNode(State state, List t) {
        super(state, t);
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }

    protected Segment containerSegmentForChild(TranspilerNode child, boolean isFirstChild) {
        var segment = new Segment(child);
        if (!isFirstChild) {
            segment.setHead(" UNION (");
        } else {
            segment.setHead("(");
        }
        segment.addSegmentToBody(child.toSegment());
        segment.setTail(")");
        return segment;
    }

    @Override
    public Segment toSegment() {
        var topLevel = new Segment(this);
        topLevel.setHead("SELECT collect_list(" + Standards.SINGLE_VALUE_COLUMN_NAME + ") AS " + Standards.SINGLE_VALUE_COLUMN_NAME + " FROM (");

        if (getChildren().size() == 0) {
            var emptySegment = new Segment(this);
            emptySegment.setHead(Standards.EMPTY_TABLE);
            topLevel.addSegmentToBody(emptySegment);
        } else {
            boolean first = true;
            for (var child : getChildren()) {
                topLevel.addSegmentToBody(containerSegmentForChild(child, first));
                first = false;
            }
        }

        topLevel.setTail(")");
        return topLevel;
    }
}
