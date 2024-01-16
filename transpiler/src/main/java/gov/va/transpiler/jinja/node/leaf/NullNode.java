package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.Null;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class NullNode extends Leaf<Null> {

    public NullNode(State state, Null t) {
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
        segment.setHead("NULL");
        return segment;
    }
}
