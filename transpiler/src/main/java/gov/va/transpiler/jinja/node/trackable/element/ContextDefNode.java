package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.ContextDef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class ContextDefNode extends ElementNode<ContextDef> {

    public ContextDefNode(State state, ContextDef cqlEquivalent) {
        super(state, cqlEquivalent);
        state.setContext(getCqlEquivalent().getName());
    }

    @Override
    public Segment toSegment() {
        // Not printable
        throw new UnsupportedOperationException();
    }
}
