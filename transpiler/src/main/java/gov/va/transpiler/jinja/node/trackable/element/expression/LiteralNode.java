package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.Literal;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class LiteralNode extends ExpressionNode<Literal> {

    public enum LiteralType {
        Unsupported,
        Integer,
        String
    }

    public LiteralNode(State state, Literal t) {
        super(state, t);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead(Standards.MACRO_FILE_NAME + ".Literal('" + getCqlEquivalent().getValueType().getLocalPart() + "', '" + getCqlEquivalent().getValue() + "')");
        return segment;
    }
}
