package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.UsingDef;

public class UsingDefNode extends AbstractNodeNoChildren<UsingDef> {
    // Database tables should already be set up in a proper schema. We shouldn't need to do anything to support "using" statements.
    @Override
    public String asOneLine() {
        return null;
    }
}
