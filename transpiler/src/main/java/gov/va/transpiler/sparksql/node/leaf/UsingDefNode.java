package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.AbstractNodeNoChildren;

public class UsingDefNode extends AbstractNodeNoChildren {
    // Database tables should already be set up in a proper schema. We shouldn't need to do anything to support "using" statements.
    @Override
    public String asOneLine() {
        return null;
    }
}
