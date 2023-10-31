package gov.va.transpiler.bulk.sparksql.node;

public class ToDecimalNode extends AbstractNodeOneChild {

    @Override
    public String asOneLine() {
        return "SELECT 0.0 + _val AS _val FROM (" + getChild().asOneLine() + ")";
    }
}
