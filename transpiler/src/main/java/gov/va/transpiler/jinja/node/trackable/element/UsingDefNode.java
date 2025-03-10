package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.UsingDef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class UsingDefNode extends ElementNode<UsingDef> {

    public UsingDefNode(State state, UsingDef t) {
        super(state, t);
    }

    @Override
    public Segment toSegment() {
        // Not printable
        throw new UnsupportedOperationException();
    }
}
