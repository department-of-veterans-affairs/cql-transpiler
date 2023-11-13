package gov.va.transpiler.sparksql.node.binary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import gov.va.transpiler.sparksql.node.Binary;

public class BinaryOperatorNode extends Binary {

    public BinaryOperatorNode(String operator) {
        setName(operator);
    }

    @Override
    public String asOneLine() {
        return "SELECT " + "(" + getChild1().asOneLine() + ") " + getName() + " (" + getChild2().asOneLine() + ") " + SINGLE_VALUE_COLUMN_NAME;
    }
}
