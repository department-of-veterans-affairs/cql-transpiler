package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.Literal;

import gov.va.transpiler.jinja.printing.Segment;
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

    public LiteralType getTypeForLiteral() {
        switch (getCqlEquivalent().getResultType().toString()) {
            case "System.Integer" :
                return LiteralType.Integer;
            case "System.String" :
                return LiteralType.String;
            default:
        }
        return LiteralType.Unsupported;
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
        var type = getTypeForLiteral();
        switch (type) {
            case Integer:
                segment.setHead(getCqlEquivalent().getValue());
                break;
            case String:
                segment.setHead("'" + getCqlEquivalent().getValue() + "'");
                break;
            default:
                segment.setHead("Unsupported Literal Type");
                break;
        }
        return segment;
    }
}
