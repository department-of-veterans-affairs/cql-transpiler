package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.ParameterDef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class ParameterDefNode extends ElementNode<ParameterDef> {

    public ParameterDefNode(State state, ParameterDef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }

    @Override
    public Segment toSegment() {
        // Not printable
        throw new UnsupportedOperationException();
    }
}
