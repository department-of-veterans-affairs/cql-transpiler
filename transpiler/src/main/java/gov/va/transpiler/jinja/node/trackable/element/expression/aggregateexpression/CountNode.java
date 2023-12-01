package gov.va.transpiler.jinja.node.trackable.element.expression.aggregateexpression;

import org.hl7.elm.r1.Count;

import gov.va.transpiler.jinja.state.State;

public class CountNode extends AggregateExpressionNode<Count> {

    public CountNode(State state, Count cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public int allowedNumberOfChildren() {
        return 1;
    }
}
