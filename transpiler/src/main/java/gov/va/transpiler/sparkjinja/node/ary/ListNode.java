package gov.va.transpiler.sparkjinja.node.ary;

import org.hl7.elm.r1.List;

import gov.va.transpiler.sparkjinja.node.TranspilerNode;
import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.standards.Standards;
import gov.va.transpiler.sparkjinja.state.State;

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
    protected Segment childToSegment(TranspilerNode child) {
        return containerizer.childToSegmentContainerizing(child);
    }

    @Override
    public Segment toSegment() {
        return toSegmentWithJoinedChildren("SELECT collect_list(" + Standards.SINGLE_VALUE_COLUMN_NAME + ") AS " + Standards.SINGLE_VALUE_COLUMN_NAME + " FROM (", ")", "(", ")", " UNION ");
    }
}
