package gov.va.transpiler.jinja.node.trackable.element.expression.operatorexpression.binaryexpression;

import org.hl7.elm.r1.Expression;
import org.hl7.elm.r1.In;
import org.hl7.elm.r1.IntervalTypeSpecifier;

import gov.va.transpiler.jinja.node.CQLEquivalent;
import gov.va.transpiler.jinja.state.State;

public class InNode extends BinaryExpressionNode<In> {

    public InNode(State state, In cqlEquivalent) {
        super(state, cqlEquivalent);
    }

    @Override
    public String getOperator() {
        String postfix = "";
        if (((CQLEquivalent<?>) getRight()).getCqlEquivalent() instanceof Expression) {
            var right = (Expression) ((CQLEquivalent<?>) getRight()).getCqlEquivalent();
            if (right.getResultTypeSpecifier() instanceof IntervalTypeSpecifier) {
                postfix = "Interval";
            }
        }
        return super.getOperator() + postfix;
    }
}
