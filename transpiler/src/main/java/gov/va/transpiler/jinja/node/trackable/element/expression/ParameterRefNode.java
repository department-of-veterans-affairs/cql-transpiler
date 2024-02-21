package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.ParameterRef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class ParameterRefNode extends ExpressionNode<ParameterRef> {

    public ParameterRefNode(State state, ParameterRef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public Type getType() {
        return Type.LAZY_EVALUATION;
    }

    @Override
    public Segment toSegment() {
        return new Segment(getName() + "('" + getCqlEquivalent().getName() + "')");
    }
}
