package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.List;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class ListNode extends ExpressionNode<List> {

    private String context;

    public ListNode(State state, List cqlEquivalent) {
        super(state, cqlEquivalent);
        context = state.getContext();
    }

    @Override
    public TranspilerNode getChildByReference(String nameOrIndex) {
        return getChildren().get(Integer.parseInt(nameOrIndex));
    }

    @Override
    public Type getType() {
        return Type.ENCAPSULATED_SIMPLE;
    }

    @Override
    public Segment childToSegment(TranspilerNode child) {
        if (child.getType() == Type.TABLE) {
            return childToSegmentCollectTable(context, child);
        } else if (child.getType() == Type.SIMPLE) {
            return childToSegmentEncapsulateSimple(child);
        } else {
            return super.childToSegment(child);
        }
    }

    @Override
    public Segment toSegment() {
        return toSegmentWithJoinedChildren(getChildren(), getName() + "([", "])", "", "", ", ");
    }
}
