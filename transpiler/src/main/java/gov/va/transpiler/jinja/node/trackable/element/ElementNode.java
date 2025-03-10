package gov.va.transpiler.jinja.node.trackable.element;

import org.hl7.elm.r1.Element;

import gov.va.transpiler.jinja.node.trackable.TrackableNode;
import gov.va.transpiler.jinja.state.State;

public class ElementNode<T extends Element> extends TrackableNode<T> {

    public ElementNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
