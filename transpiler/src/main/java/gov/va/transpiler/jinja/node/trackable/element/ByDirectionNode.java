package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.ByDirection;

import gov.va.transpiler.jinja.state.State;

public class ByDirectionNode extends SortByItemNode<ByDirection> {

    public ByDirectionNode(State state, ByDirection cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
