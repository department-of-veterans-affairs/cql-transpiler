package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.OperandRef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class OperandRefNode extends ExpressionNode<OperandRef> {

    public static final String REFERENCE_TYPE = "Operand";

    public OperandRefNode(State state, OperandRef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead(getCqlEquivalent().getName());
        return segment;
    }
}
