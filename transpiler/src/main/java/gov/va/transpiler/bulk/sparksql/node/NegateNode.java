package gov.va.transpiler.bulk.sparksql.node;

import static gov.va.transpiler.bulk.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import org.hl7.elm.r1.Negate;

public class NegateNode extends AbstractNodeOneChild<Negate> {

    @Override
    public String asOneLine() {
        return "SELECT -1 * " + SINGLE_VALUE_COLUMN_NAME + " AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (" + getChild().asOneLine() + ")";
    }
}
