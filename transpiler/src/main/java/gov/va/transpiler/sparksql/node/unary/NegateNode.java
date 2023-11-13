package gov.va.transpiler.sparksql.node.unary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import gov.va.transpiler.sparksql.node.Unary;

public class NegateNode extends Unary {

    @Override
    public String asOneLine() {
        return "SELECT -1 * " + SINGLE_VALUE_COLUMN_NAME + " AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (" + getChild().asOneLine() + ")";
    }
}
