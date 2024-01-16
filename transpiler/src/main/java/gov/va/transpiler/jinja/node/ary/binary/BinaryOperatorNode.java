package gov.va.transpiler.jinja.node.ary.binary;

import org.hl7.elm.r1.BinaryExpression;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.leaf.LiteralNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public abstract class BinaryOperatorNode<T extends BinaryExpression> extends Binary<T> {

    public BinaryOperatorNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    protected abstract String getOperator();

    @Override
    public Segment toSegment() {
        return toSegmentWithJoinedChildren("", "", "(", ")", " " + getOperator() + " ", getOperator() + " ");
    }

    @Override
    public Segment childToSegment(TranspilerNode child) {
        if (child instanceof LiteralNode) {
            return super.childToSegment(child);
        } else {
            var segment = new Segment();
            segment.setHead("(");
            segment.setTail(")");
            var childSegment = super.childToSegment(child);
            segment.addChild(childSegment);
            return segment;
        }
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
