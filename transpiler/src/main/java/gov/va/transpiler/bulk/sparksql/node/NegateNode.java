package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.Negate;

public class NegateNode extends AbstractNodeOneChild<Negate> {

    @Override
    public String asOneLine() {
        return "SELECT -1 * _val AS _val FROM (" + getChild().asOneLine() + ")";
    }
}
