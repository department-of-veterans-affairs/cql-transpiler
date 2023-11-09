package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.Retrieve;

public class RetrieveNode extends AbstractNodeNoChildren<Retrieve> {

    private String cqlContext;

    public String getCqlContext() {
        return cqlContext;
    }

    public void setCqlContext(String cqlContext) {
        this.cqlContext = cqlContext;
    }

    public String asOneLineWithAlias(String alias) {
        return alias == null ? "SELECT * FROM " + getName() : "SELECT * FROM " + getName() + " AS " + alias;
    }

    @Override
    public String asOneLine() {
        return asOneLineWithAlias(null);
    }

    @Override
    public boolean isTable() {
        return true;
    }
}
