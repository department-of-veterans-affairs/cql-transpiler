package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.Literal;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class LiteralNode extends Leaf<Literal> {

    public enum LiteralType {
        Unsupported,
        Integer,
        String
    }

    public LiteralNode(State state, Literal t) {
        super(state, t);
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public boolean isSimpleValue() {
        return true;
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead(Standards.MACRO_FILE_NAME + ".Literal('" + getCqlEquivalent().getValueType().getLocalPart() + "', '" + getCqlEquivalent().getValue() + "')");
        return segment;
    }
}
