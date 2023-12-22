package gov.va.transpiler.jinja.node.ary.binary;

import org.hl7.elm.r1.BinaryExpression;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public abstract class BinaryOperatorNode<T extends BinaryExpression> extends Binary<T> {

    public BinaryOperatorNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    protected abstract String getOperator();

    @Override
    public Segment toSegment() {
        return toSegmentWithJoinedChildren("", "", "(", ")", getOperator(), getOperator() + " ");
    }

    @Override
    public boolean isSimpleValue() {
        return true;
    }

    @Override
    public boolean isTable() {
        return false;
    }
}
