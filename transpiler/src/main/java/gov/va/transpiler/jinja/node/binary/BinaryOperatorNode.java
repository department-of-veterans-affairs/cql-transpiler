package gov.va.transpiler.jinja.node.binary;

import org.hl7.elm.r1.BinaryExpression;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public abstract class BinaryOperatorNode<T extends BinaryExpression> extends Binary<T> {

    public BinaryOperatorNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    protected abstract String getOperator();

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        var leftSegment = getLeft().toSegment();
        var rightSegment = getRight().toSegment();
        var split = !(leftSegment.getPrintType() == PrintType.Inline && rightSegment.getPrintType() == PrintType.Inline);
        var joiner = new Segment();
        joiner.setHead(split ? getOperator() : getOperator() + " ");
        segment.addChild(leftSegment);
        segment.addChild(joiner);
        segment.addChild(rightSegment);
        return segment;
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
