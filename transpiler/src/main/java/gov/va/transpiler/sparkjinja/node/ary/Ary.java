package gov.va.transpiler.sparkjinja.node.ary;

import static gov.va.transpiler.sparksql.utilities.Standards.EMPTY_TABLE;

import java.util.ArrayList;
import java.util.List;

import org.cqframework.cql.elm.tracking.Trackable;

import gov.va.transpiler.sparkjinja.node.CQLEquivalent;
import gov.va.transpiler.sparkjinja.node.TranspilerNode;
import gov.va.transpiler.sparkjinja.node.UnsupportedChildNodeException;
import gov.va.transpiler.sparkjinja.node.unsupported.DisabledNode;
import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

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

    protected Segment childToSegment(TranspilerNode child) {
        return child.toSegment();
    }

    protected Segment toSegmentWithJoinedChildren(String head, String tail, String childPrefix, String childPostfix, String childJoinerInline) {
        var topLevel = new Segment();
        topLevel.setHead(head);
        switch(getChildren().size()) {
            case 0:
                var emptySegment = new Segment();
                emptySegment.setHead(EMPTY_TABLE);
                topLevel.addChild(emptySegment);
                break;
            case 1:
                topLevel.addChild(childToSegment(getChildren().get(0)));
                break;
            default:
                for (int i = 0; i < getChildren().size(); i++) {
                    // Prefix
                    var prefixSegment = new Segment();
                    prefixSegment.setHead(childPrefix);
                    topLevel.addChild(prefixSegment);

                    // Child
                    topLevel.addChild(childToSegment(getChildren().get(i)));

                    // Postfix
                    var postfixSegment = new Segment();
                    boolean last = i == getChildren().size() - 1;
                    postfixSegment.setHead(last ? childPostfix : childPostfix + (childJoinerInline));
                    topLevel.addChild(postfixSegment);
                }
        }
        topLevel.setTail(tail);
        return topLevel;
    }
}