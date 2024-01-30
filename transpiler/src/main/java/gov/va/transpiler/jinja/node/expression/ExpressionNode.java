package gov.va.transpiler.jinja.node.expression;

import org.hl7.elm.r1.Expression;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.state.State;

public abstract class ExpressionNode<T extends Expression> extends CQLEquivalent<T> {

    public ExpressionNode(State state, T cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public boolean isSimpleValue() {
        return true;
    }

    @Override
    public boolean isTable() {
        return false;
    }
}
