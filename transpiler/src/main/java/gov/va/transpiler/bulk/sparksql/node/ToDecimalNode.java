package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.ToDecimal;

public class ToDecimalNode extends AbstractNodeOneChild<ToDecimal> {

    @Override
    public String asOneLine() {
        return "SELECT 0.0 + _val AS _val FROM (" + getChild().asOneLine() + ")";
    }
}
