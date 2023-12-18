package gov.va.transpiler.jinja.node.utility;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.standards.Standards;

public class Containerizer {

    /**
     * @param child Transpiler node
     * @return If the child is a simple value, converts the child into a segment and wraps that segment into another segment representing a conversion from a simple value to a table.
     * Otherwise returns the child as a segment.
     */
    public Segment childToSegmentContainerizingIfSimpleValue(TranspilerNode child) {
        if (child.isSimpleValue()) {
            var wrapper = new Segment(child);
            wrapper.setHead("SELECT ");
            wrapper.setTail(" " + Standards.SINGLE_VALUE_COLUMN_NAME);
            wrapper.addSegmentToBody(child.toSegment());
            return wrapper;
        }
        return child.toSegment();
    }
}
