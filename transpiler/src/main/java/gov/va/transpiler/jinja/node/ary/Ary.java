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

    protected Segment toSegmentWithJoinedChildren(String head, String tail, String childJoiner) {
        var topLevel = new Segment();
        topLevel.setHead(head);
        boolean firstChild = true;
        boolean onlyChild = getChildren().size() == 1;
        if (getChildren().size() == 0) {
            var emptySegment = new Segment();
            emptySegment.setHead(EMPTY_TABLE);
            topLevel.addChild(emptySegment);
        } else {
            for (var child : getChildren()) {
                Segment childSegment = childToSegment(child);
                if (onlyChild) {
                    topLevel.addChild(childSegment);
                } else {
                    var prefixSegment = new Segment();
                    prefixSegment.setHead(firstChild ? "(" : childJoiner + "(");
                    topLevel.addChild(prefixSegment);
                    var postFixSegment = new Segment();
                    postFixSegment.setTail(")");
                    topLevel.addChild(postFixSegment);
                    var elementContainer = child.toSegment();
                    topLevel.addChild(elementContainer);
                }
                firstChild = false;
            }
        }
        topLevel.setTail(tail);
        return topLevel;
    }
}
