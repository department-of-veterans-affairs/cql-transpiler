package gov.va.transpiler.bulk.sparksql.node;

import static gov.va.transpiler.bulk.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import org.hl7.elm.r1.OperatorExpression;

public class BinaryOperatorNode<T extends OperatorExpression> extends AbstractNodeBinaryExpression<T> {

    public BinaryOperatorNode(String operator) {
        setName(operator);
    }

    @Override
    public String asOneLine() {
        return "SELECT " + "(" + getChild1().asOneLine() + ") " + getName() + " (" + getChild2().asOneLine() + ") " + SINGLE_VALUE_COLUMN_NAME;
    }
}
