package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.unaryexpression;

import org.hl7.elm.r1.End;

import gov.va.transpiler.jinja.state.State;

public class EndNode extends UnaryExpressionNode<End> {

    public EndNode(State state, End cqlEquivalent) {
        super(state, cqlEquivalent);
    }
}
