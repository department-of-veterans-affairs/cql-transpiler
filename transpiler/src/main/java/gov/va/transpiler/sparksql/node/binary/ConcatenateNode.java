package gov.va.transpiler.sparksql.node.binary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import gov.va.transpiler.sparksql.node.AbstractNodeBinaryExpression;

public class ConcatenateNode extends AbstractNodeBinaryExpression {

    @Override
    public String asOneLine() {
        return "SELECT concat(leftval, rightval) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM ((SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS leftval FROM " + getChild1().asOneLine() + ") OUTER JOIN ((SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS rightval FROM " + getChild1().asOneLine() + "))))";
    }
    
}
