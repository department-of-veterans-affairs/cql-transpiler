package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.List;

import gov.va.transpiler.jinja.state.State;

public class ListNode extends ExpressionNode<List> {

    public ListNode(State state, List cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
