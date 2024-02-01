package gov.va.transpiler.jinja.node.element;

import org.hl7.elm.r1.ValueSetDef;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class ValueSetDefNode extends CQLEquivalent<ValueSetDef> {

    public ValueSetDefNode(State state, ValueSetDef t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        // Not printable
        throw new UnsupportedOperationException();
    }
}
