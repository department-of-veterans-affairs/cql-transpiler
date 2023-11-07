package gov.va.transpiler.bulk.sparksql.node;

import org.hl7.elm.r1.ExpressionRef;

public class ExpressionRefNode extends AbstractNodeNoChildren<ExpressionRef> {

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
