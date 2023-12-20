package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.Retrieve;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class RetrieveNode extends Leaf<Retrieve> {

    public RetrieveNode(State state, Retrieve t) {
        super(state, t);
    }

    @Override
    public boolean isTable() {
        return true;
    }

    @Override
    public boolean isSimpleValue() {
        return false;
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("SELECT * FROM " + getCqlEquivalent().getDataType().getLocalPart());
        return segment;
    }
}
