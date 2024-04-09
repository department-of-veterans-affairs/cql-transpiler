package gov.va.transpiler.jinja.node.trackable.element.typespecifier;

import org.hl7.elm.r1.IntervalTypeSpecifier;

import gov.va.transpiler.jinja.state.State;

public class IntervalTypeSpecifierNode extends TypeSpecifierNode<IntervalTypeSpecifier> {

    public IntervalTypeSpecifierNode(State state, IntervalTypeSpecifier cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
