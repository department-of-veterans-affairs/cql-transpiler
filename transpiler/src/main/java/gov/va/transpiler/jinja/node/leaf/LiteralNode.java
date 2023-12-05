package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.Literal;

import gov.va.transpiler.jinja.printing.Segment;

public class LiteralNode extends Leaf<Literal> {

    public enum LiteralType {
        Unsupported,
        Integer,
        String
    }

    public LiteralNode(Literal t) {
        super(t);
    }

    @Override
    public boolean isTable() {
        return false;
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
