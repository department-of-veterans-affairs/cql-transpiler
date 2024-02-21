package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.Flatten;

import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class FlattenNode extends UnaryExpressionNode<Flatten> {

    private String context;

    public FlattenNode(State state, Flatten cqlEquivalent) {
        super(state, cqlEquivalent);
        context = state.getContext();
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
}
