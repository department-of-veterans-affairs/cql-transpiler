package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.SortByItem;

import gov.va.transpiler.jinja.state.State;

public class SortByItemNode<T extends SortByItem> extends ElementNode<T> {

    public SortByItemNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
