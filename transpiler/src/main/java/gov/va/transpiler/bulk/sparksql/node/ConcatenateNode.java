package gov.va.transpiler.bulk.sparksql.node;

import static gov.va.transpiler.bulk.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import org.hl7.elm.r1.Concatenate;

public class ConcatenateNode extends AbstractNodeBinaryExpression<Concatenate> {

    @Override
    public String asOneLine() {
        return "SELECT concat(leftval, rightval) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM ((SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS leftval FROM " + getChild1().asOneLine() + ") OUTER JOIN ((SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS rightval FROM " + getChild1().asOneLine() + "))))";
    }
    
}
