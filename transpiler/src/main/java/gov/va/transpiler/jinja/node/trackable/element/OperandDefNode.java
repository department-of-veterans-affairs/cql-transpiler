package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.OperandDef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class OperandDefNode extends ElementNode<OperandDef> {

    public static final String REFERENCE_TYPE = "operand";

    public OperandDefNode(State state, OperandDef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public Segment toSegment() {
        var segment = new Segment();
        segment.setHead(getCqlEquivalent().getName());
        return segment;
    }
}
