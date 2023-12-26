package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.IdentifierRef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class IdentifierRefNode extends Leaf<IdentifierRef> {

    public IdentifierRefNode(State state, IdentifierRef t) {
        super(state, t);
    }

    @Override
    public boolean isTable() {
        return false;
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }

    @Override
    public boolean isColumnReference() {
        return true;
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead(getCqlEquivalent().getName());
        return segment;
    }
}
