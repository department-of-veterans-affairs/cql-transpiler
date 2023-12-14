package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.OperandDef;

import gov.va.transpiler.jinja.converter.State;
import gov.va.transpiler.jinja.printing.Segment;

public class OperandDefNode extends Leaf<OperandDef> {

    public OperandDefNode(State state, OperandDef t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment(this);
        segment.setHead(getReferenceName());
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

    @Override
    public PrintType getPrintType() {
        return PrintType.Inline;
    }

    @Override
    public String getReferenceName() {
        return getCqlEquivalent().getName();
    }
}
