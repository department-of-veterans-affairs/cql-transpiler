package gov.va.transpiler.bulk.sparksql.node;

import static gov.va.transpiler.bulk.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import org.hl7.elm.r1.ToDecimal;

public class ToDecimalNode extends AbstractNodeOneChild<ToDecimal> {

    @Override
    public String asOneLine() {
        return "SELECT 0.0 + " + SINGLE_VALUE_COLUMN_NAME + " AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (" + getChild().asOneLine() + ")";
    }
}
