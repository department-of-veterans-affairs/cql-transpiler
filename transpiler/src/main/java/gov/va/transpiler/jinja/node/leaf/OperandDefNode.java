package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.OperandDef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class OperandDefNode extends Leaf<OperandDef> {

    public OperandDefNode(State state, OperandDef t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment(this);
        segment.setHead(getCqlEquivalent().getName());
        return segment;
    }

    @Override
    public boolean isTable() {
        // TODO
        return false;
    }

    @Override
    public boolean isSimpleValue() {
        // TODO
        return true;
    }
}
