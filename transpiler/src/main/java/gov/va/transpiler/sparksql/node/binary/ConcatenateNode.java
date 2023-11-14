package gov.va.transpiler.sparksql.node.binary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import gov.va.transpiler.sparksql.node.Binary;

public class ConcatenateNode extends Binary {

    @Override
    public String asOneLine() {
        if (!isEncapsulated()) {
            return "concat(" + getChild1().asOneLine() + ", " + getChild2().asOneLine() + ")";
        } else if (!getChild1().isEncapsulated()) {
            return "SELECT concat(" + getChild1().asOneLine() + ", rightval) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS leftval FROM (" + getChild1().asOneLine() + "))";
        } else if (!getChild2().isEncapsulated()) {
            return "SELECT concat(leftval, " + getChild2().asOneLine() + ") AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS rightval FROM (" + getChild2().asOneLine() + "))";
        } else {
            return "SELECT concat(leftval, rightval) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM ((SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS leftval FROM " + getChild1().asOneLine() + ") OUTER JOIN ((SELECT " + SINGLE_VALUE_COLUMN_NAME + " AS rightval FROM " + getChild1().asOneLine() + "))))";
        }
    }
}
