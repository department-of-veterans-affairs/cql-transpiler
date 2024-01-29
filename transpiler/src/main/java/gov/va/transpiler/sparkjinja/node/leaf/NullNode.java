package gov.va.transpiler.sparkjinja.node.leaf;

import org.hl7.elm.r1.Null;

import gov.va.transpiler.sparkjinja.printing.Segment;
import gov.va.transpiler.sparkjinja.state.State;

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
