package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.Leaf;

public class ExpressionRefNode extends Leaf {

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
