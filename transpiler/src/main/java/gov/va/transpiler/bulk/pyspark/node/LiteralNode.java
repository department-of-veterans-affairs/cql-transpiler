package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.bulk.pyspark.utilities.CQLTypeToPythonType;
import gov.va.transpiler.node.TerminalNode;

public class LiteralNode extends TerminalNode {

    private final CQLTypeToPythonType cqlTypeToPythonType;

    private String resultType;

    public LiteralNode(CQLTypeToPythonType cqlTypeToPythonType) {
        this.cqlTypeToPythonType = cqlTypeToPythonType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    @Override
    public String asOneLine() {
        return cqlTypeToPythonType.toPythonRepresentation(getName(), cqlTypeToPythonType.asPythonType(getResultType()));
    }
}
