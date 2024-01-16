package gov.va.transpiler.jinja.node.utility;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.standards.Standards;

public class Containerizer {

    /**
     * @param child Child of a parent node that has to containerize its children.
     * @return Child containerized into a list of structs. Used to take tables and fit them into single entries inside e.g. lists, tuples.
     */
    public Segment containerizeTable(TranspilerNode child) {
        // "SELECT collect_list(struct(*)) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (" + child.asOneLine() + ")"
        var toListOfStructs = new Segment();
        toListOfStructs.setHead("SELECT collect_list(struct(*)) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (");
        toListOfStructs.addChild(child.toSegment());
        toListOfStructs.setTail(")");
        return toListOfStructs;
    }

    /**
     * @param child Child of a parent node that has to containerize its children.
     * @return Child containerized from a simple node into a node that can be saved as a table.
     */
    public Segment containerizeSimpleValue(TranspilerNode child) {
        var toAccessibleAsTable = new Segment();
        toAccessibleAsTable.setHead("SELECT ");
        toAccessibleAsTable.setTail(" " + Standards.SINGLE_VALUE_COLUMN_NAME);
        toAccessibleAsTable.addChild(child.toSegment());
        return toAccessibleAsTable;
    }

    /**
     * @param child Transpiler node
     * @return If the child is a simple value, converts the child into a segment and wraps that segment into another segment representing a conversion from a simple value to a table.
     * Otherwise returns the child as a segment.
     */
    public Segment childToSegmentContainerizing(TranspilerNode child) {
        Segment segment;
        if (child.isTable()) {
            segment = containerizeTable(child);
        } else if (child.isSimpleValue()) {
            segment = containerizeSimpleValue(child);
        } else {
            segment = child.toSegment();
        }
        return segment;
    }
}
