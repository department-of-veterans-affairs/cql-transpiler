package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.naryexpression;

import org.hl7.elm.r1.Union;

import gov.va.transpiler.jinja.state.State;

public class UnionNode extends NaryExpressionNode<Union> {

    public UnionNode(State state, Union cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public Type getType() {
        return Type.TABLE;
    }
    
}
