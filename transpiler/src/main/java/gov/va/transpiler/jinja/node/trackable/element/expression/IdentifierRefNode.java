package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.IdentifierRef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.printing.Segment.PrintType;
import gov.va.transpiler.jinja.state.State;

public class IdentifierRefNode extends ExpressionNode<IdentifierRef> {

    public IdentifierRefNode(State state, IdentifierRef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public Type getType() {
        return Type.COLUMN_REFERENCE;
    }

    @Override
    public Segment toSegment() {
        return new Segment(getName() + "('" + getCqlEquivalent().getName(), "')", PrintType.Inline);
    }
}
