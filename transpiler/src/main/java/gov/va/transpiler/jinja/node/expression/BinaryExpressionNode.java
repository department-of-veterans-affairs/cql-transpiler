package gov.va.transpiler.jinja.node.expression;

import org.hl7.elm.r1.BinaryExpression;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.node.unsupported.DisabledNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class BinaryExpressionNode<T extends BinaryExpression> extends OperatorExpressionNode<T> {

    private TranspilerNode left;
    private TranspilerNode right;

    public BinaryExpressionNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (!(child instanceof DisabledNode)) {
            if (left == null) {
                left = child;
            } else if (right == null) {
                right = child;
            } else {
                throw new UnsupportedChildNodeException(this, child);
            }
        }
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead(Standards.MACRO_FILE_NAME + "." + getCqlEquivalent().getClass().getSimpleName() + "(");
        segment.addChild(left.toSegment());
        var commaSegment = new Segment();
        commaSegment.setHead(", ");
        segment.addChild(commaSegment);
        segment.addChild(right.toSegment());
        segment.setTail(")");
        return segment;
    }
}
