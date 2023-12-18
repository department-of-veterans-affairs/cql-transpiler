package gov.va.transpiler.jinja.node.ary;

import org.hl7.elm.r1.List;

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

    @Override
    public Segment toSegment() {
        var topLevel = new Segment(this);
        topLevel.setHead("SELECT collect_list(" + Standards.SINGLE_VALUE_COLUMN_NAME + ") AS " + Standards.SINGLE_VALUE_COLUMN_NAME + " FROM (");

        if (getChildren().size() == 0) {
            var emptySegment = new Segment(this);
            emptySegment.setHead(Standards.EMPTY_TABLE);
            topLevel.addSegmentToBody(emptySegment);
        } else {
            boolean firstChild = true;
            boolean onlyChild = getChildren().size() == 1;
            for (var child : getChildren()) {
                Segment childSegment = containerizer.childToSegmentContainerizing(child);
                if (onlyChild) {
                    topLevel.addSegmentToBody(childSegment);
                } else {
                    var unionContainer = new Segment(child);
                    unionContainer.setHead(firstChild ? "(" : " UNION (");
                    unionContainer.setTail(")");
                    unionContainer.addSegmentToBody(childSegment);
                    topLevel.addSegmentToBody(unionContainer);
                }
                firstChild = false;
            }
        }

        topLevel.setTail(")");
        return topLevel;
    }
}
