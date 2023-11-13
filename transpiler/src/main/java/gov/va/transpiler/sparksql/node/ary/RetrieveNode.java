package gov.va.transpiler.sparksql.node.ary;

import gov.va.transpiler.sparksql.node.Ary;

public class RetrieveNode extends Ary {

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
