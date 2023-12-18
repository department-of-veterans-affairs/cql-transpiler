package gov.va.transpiler.jinja.node.ary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import org.hl7.elm.r1.Tuple;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.node.unary.TupleElementNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class TupleNode extends Ary<Tuple> {

    public TupleNode(State state, Tuple t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (!(child instanceof TupleElementNode)) {
            throw new UnsupportedChildNodeException(this, child);
        }
        super.addChild(child);
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
        return toSegmentWithJoinedChildren("SELECT struct(*) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (", ")", ", ");
    }
}
