package gov.va.transpiler.bulk.sparksql.node;

public class ExpressionRefNode extends AbstractNodeNoChildren {

    private boolean isTable;

    @Override
    public String asOneLine() {
        return "SELECT * FROM " + getName();
    }

    @Override
    public boolean isTable() {
        return isTable;
    }

    public void setTable(boolean isTable) {
        this.isTable = isTable;
    }
}
