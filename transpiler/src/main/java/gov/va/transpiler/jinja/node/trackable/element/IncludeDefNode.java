package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.IncludeDef;

import gov.va.transpiler.jinja.printing.Segment;
import gov.va.transpiler.jinja.state.State;

public class IncludeDefNode extends ElementNode<IncludeDef> {

    public IncludeDefNode(State state, IncludeDef cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public Segment toSegment() {
        return new Segment("{% import '" + getCqlEquivalent().getLocalId() + " ' as " + "?" + " %}");
    }
}
