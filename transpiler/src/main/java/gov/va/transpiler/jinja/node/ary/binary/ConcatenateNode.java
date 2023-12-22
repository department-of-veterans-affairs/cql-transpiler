package gov.va.transpiler.jinja.node.ary.binary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import org.hl7.elm.r1.Concatenate;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class ConcatenateNode extends Binary<Concatenate> {

    public ConcatenateNode(State state, Concatenate t) {
        super(state, t);
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public boolean isSimpleValue() {
        return getLeft().isSimpleValue() && getRight().isSimpleValue();
    }

    @Override
    public Segment toSegment() {
        Segment segment = new Segment();
        if (isSimpleValue()) {
            segment = toSegmentWithJoinedChildren("concat(",")","","",", ",", ");
        } else if (getLeft().isSimpleValue()) {
            var concatSegment = new Segment();
            concatSegment.setHead("SELECT concat(");
            concatSegment.addChild(childToSegment(getLeft()));
            concatSegment.setTail(", rightval) AS " + SINGLE_VALUE_COLUMN_NAME);
            var fromSegment = new Segment();
            fromSegment.setHead(" FROM (SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS rightval FROM (");
            fromSegment.addChild(childToSegment(getRight()));
            segment.addChild(concatSegment);
            segment.addChild(fromSegment);
        } else if (!getRight().isSimpleValue()) {
            var concatSegment = new Segment();
            concatSegment.setHead("SELECT concat(leftval, ");
            concatSegment.addChild(childToSegment(getRight()));
            concatSegment.setTail(") AS " + SINGLE_VALUE_COLUMN_NAME);
            var fromSegment = new Segment();
            fromSegment.setHead(" FROM (SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS leftval FROM (");
            fromSegment.addChild(childToSegment(getLeft()));
            segment.addChild(concatSegment);
            segment.addChild(fromSegment);
        } else {
            segment = toSegmentWithJoinedChildren("SELECT concat(leftval, rightval) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM ((SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS leftval FROM ",
              "))))",null, null,
               ") OUTER JOIN ((SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS rightval FROM ",
               ") OUTER JOIN ((SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS rightval FROM ");
        }
        return segment;
    }
}