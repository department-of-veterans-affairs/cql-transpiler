package gov.va.transpiler.jinja.node.trackable.element.expression;

import org.hl7.elm.r1.Tuple;

import gov.va.transpiler.jinja.state.State;

public class TupleNode extends ExpressionNode<Tuple> {

    public TupleNode(State state, Tuple cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
