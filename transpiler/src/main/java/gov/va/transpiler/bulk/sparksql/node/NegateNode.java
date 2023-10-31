package gov.va.transpiler.bulk.sparksql.node;

public class NegateNode extends AbstractNodeOneChild {

    @Override
    public String asOneLine() {
        return "SELECT -1 * _val AS _val FROM (" + getChild().asOneLine() + ")";
    }
}
