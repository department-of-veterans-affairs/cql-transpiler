package gov.va.transpiler.jinja.node.ary.binary;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.node.ary.Ary;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public abstract class Binary<T extends Trackable> extends Ary<T> {

    public Binary(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public void addChild(TranspilerNode child) {
        if (getChildren().size() < 2) {
            getChildren().add(child);
        } else {
            throw new UnsupportedChildNodeException(this, child);
        }
    }

    protected TranspilerNode getLeft() {
        return getChildren().get(0);
    }

    protected TranspilerNode getRight() {
        return getChildren().get(1);
    }

    @Override
    protected Segment toSegmentWithJoinedChildren(String head, String tail, String childPrefix, String childPostfix, String childJoinerInline, String childJoinerLine) {
        var segment = new Segment();
        segment.setHead(head);
        var leftSegment = childToSegment(getLeft());
        segment.addChild(leftSegment);
        var rightSegment = childToSegment(getRight());
        var joiner = new Segment();
        var split = !(leftSegment.getPrintType() == PrintType.Inline && rightSegment.getPrintType() == PrintType.Inline);
        joiner.setHead(split ? childJoinerLine : childJoinerInline);
        segment.addChild(joiner);
        segment.addChild(rightSegment);
        segment.setTail(tail);
        return segment;
    }
}