package gov.va.transpiler.sparksql.node.unary;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;
import static gov.va.transpiler.sparksql.utilities.Standards.contextToParam;

public class CountNode extends AbstractCQLAggregator {

    private String cqlContext;

    public String getCqlContext() {
        return cqlContext;
    }

    public void setCqlContext(String cqlContext) {
        this.cqlContext = cqlContext;
    }

    @Override
    public String asOneLine() {
        if (getChild().isTable()) {
            String builder = "SELECT count(*) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (" + getChild().asOneLine() + ")";
            builder += getCqlContext() == null ? "" :  " GROUP BY " + contextToParam(getCqlContext());
            return builder;
        } else {
            return "SELECT count(explode(*)) AS " + SINGLE_VALUE_COLUMN_NAME + " FROM (" + getChild().asOneLine() + ") AS " + SINGLE_VALUE_COLUMN_NAME;
        }
    }

    @Override
    public boolean isTable() {
        return getChild().isTable() && getCqlContext() != null;
    }

    @Override
    public boolean isEncapsulated() {
        return true;
    }
}
