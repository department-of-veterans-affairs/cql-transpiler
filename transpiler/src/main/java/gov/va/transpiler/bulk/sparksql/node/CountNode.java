package gov.va.transpiler.bulk.sparksql.node;

import static gov.va.transpiler.bulk.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;
import static gov.va.transpiler.bulk.sparksql.utilities.Standards.contextToParam;

import org.hl7.elm.r1.Count;

public class CountNode extends AbstractCQLAggregator<Count> {

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
            String builder = "SELECT count(*) FROM (" + getChild().asOneLine() + ")";
            builder += getCqlContext() == null ? "" :  " GROUP BY " + contextToParam(getCqlContext());
            builder += " AS " + SINGLE_VALUE_COLUMN_NAME;
            return builder;
        } else {
            return "SELECT count(explode(*)) FROM (" + getChild().asOneLine() + ") AS " + SINGLE_VALUE_COLUMN_NAME;
        }
    }
}
