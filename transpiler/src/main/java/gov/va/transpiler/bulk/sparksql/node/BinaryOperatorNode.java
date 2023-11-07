package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.OperatorExpression;

public class BinaryOperatorNode<T extends OperatorExpression> extends AbstractNodeBinaryExpression<T> {

    public BinaryOperatorNode(String operator) {
        setName(operator);
    }

    @Override
    public String asOneLine() {
        return "SELECT " + "(" + getChild1().asOneLine() + ") " + getName() + " (" + getChild2().asOneLine() + ") _val";
    }
}
