package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.ByColumn;

import gov.va.transpiler.jinja.state.State;

public class ByColumnNode extends SortByItemNode<ByColumn> {

    public ByColumnNode(State state, ByColumn cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
