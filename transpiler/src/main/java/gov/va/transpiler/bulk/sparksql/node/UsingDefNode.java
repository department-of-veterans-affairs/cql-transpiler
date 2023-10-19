package gov.va.transpiler.bulk.sparksql.node;

public class UsingDefNode extends AbstractNodeNoChildren {
    // Database tables should already be set up in a proper schema. We shouldn't need to do anything to support "using" statements.
    @Override
    public String asOneLine() {
        return null;
    }
}
