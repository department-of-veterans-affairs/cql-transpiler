package gov.va.transpiler.jinja.node.ary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import org.hl7.elm.r1.Union;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class UnionNode extends Ary<Union> {

    public UnionNode(State state, Union t) {
        super(state, t);
    }

    @Override
    public boolean isTable() {
        return getChildren().stream().anyMatch(TranspilerNode::isTable);
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }

    @Override
    public Segment childToSegment(TranspilerNode child) {
        Segment segment;
        if (child.isTable()) {
            segment = child.toSegment();
        } else {
            segment = new Segment();
            segment.setHead("SELECT explode(*) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (");
            segment.addChild(child.toSegment());
            segment.setTail(")");
        }
        return segment;
    }

    @Override
    public Segment toSegment() {
        return toSegmentWithJoinedChildren("", "", "(", ")", " UNION ", " UNION");
    }
}
