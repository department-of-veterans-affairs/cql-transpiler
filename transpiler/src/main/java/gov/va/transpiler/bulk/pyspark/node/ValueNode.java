package gov.va.transpiler.bulk.pyspark.node;

import gov.va.transpiler.bulk.pyspark.utilities.CQLTypeToPythonType;
import gov.va.transpiler.node.OutputNode;

public class ValueNode extends OutputNode {

    private final CQLTypeToPythonType cqlTypeToPythonType;

    private String value;
    private String resultType;

    public ValueNode(CQLTypeToPythonType cqlTypeToPythonType) {
        this.cqlTypeToPythonType = cqlTypeToPythonType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean addChild(OutputNode child) {
        return false;
    }

    @Override
    public String asOneLine() {
        return cqlTypeToPythonType.toPythonRepresentation(value, cqlTypeToPythonType.asPythonType(getResultType()));
    }
}
