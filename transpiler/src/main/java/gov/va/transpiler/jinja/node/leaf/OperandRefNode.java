package gov.va.transpiler.jinja.node.leaf;

import org.hl7.elm.r1.OperandRef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class OperandRefNode extends Leaf<OperandRef> {

    public OperandRefNode(State state, OperandRef t) {
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
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead("{{" + getCqlEquivalent().getName() + "}}");
        return segment;
    }
}
