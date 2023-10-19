package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.bulk.pyspark.utilities.CQLNameToPythonName;

public class ExpressionRefNode extends TerminalNode {

    private final CQLNameToPythonName cqlNameToPythonName;

    public ExpressionRefNode(CQLNameToPythonName cqlNameToPythonName) {
        this.cqlNameToPythonName = cqlNameToPythonName;
    }

    @Override
    public String asOneLine() {
        return cqlNameToPythonName.convertName(getName()) + "(spark, userData)";
    }
}
