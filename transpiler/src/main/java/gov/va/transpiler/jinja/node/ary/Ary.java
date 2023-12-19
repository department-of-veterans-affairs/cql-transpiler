package gov.va.transpiler.jinja.node.ary;

import static gov.va.transpiler.sparksql.utilities.Standards.EMPTY_TABLE;

import java.util.ArrayList;
import java.util.List;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.node.DisabledNode;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public abstract class Ary<T extends Trackable> extends CQLEquivalent<T> {

    private final List<TranspilerNode> children = new ArrayList<>();

    public Ary(State state, T t) {
        super(state, t);
    }

    @Override
    public void addChild(TranspilerNode child) throws UnsupportedChildNodeException {
    if (!(child instanceof DisabledNode)) {
        children.add(child);
    }
    }

    protected List<TranspilerNode> getChildren() {
        return children;
    }

    protected Segment childToSegment (TranspilerNode child) {
        return child.toSegment();
    }

    public PrintType getPrintType() {
        if (getChildren().size() > 3) {
            return PrintType.Line;
        }
        return PrintType.Inline;
    }

    protected Segment toSegmentWithJoinedChildren(String head, String tail, String childJoiner) {
        var topLevel = new Segment(this);
        topLevel.setHead(head);
        boolean firstChild = true;
        boolean onlyChild = getChildren().size() == 1;
        if (getChildren().size() == 0) {
            var emptySegment = new Segment(this);
            emptySegment.setHead(EMPTY_TABLE);
            topLevel.addSegmentToBody(emptySegment);
        } else {
            for (var child : getChildren()) {
                Segment childSegment = childToSegment(child);
                if (onlyChild) {
                    topLevel.addSegmentToBody(childSegment);
                } else {
                    var elementContainer = new Segment(child);
                    elementContainer.setHead(firstChild ? "(" : childJoiner + "(");
                    elementContainer.setTail(")");
                    elementContainer.addSegmentToBody(childSegment);
                    topLevel.addSegmentToBody(elementContainer);
                }
                firstChild = false;
            }
        }
        topLevel.setTail(tail);
        return topLevel;
    }
}
