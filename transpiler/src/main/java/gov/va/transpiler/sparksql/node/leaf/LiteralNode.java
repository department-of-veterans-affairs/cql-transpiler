package gov.va.transpiler.sparksql.node.leaf;

import static gov.va.transpiler.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import gov.va.transpiler.sparksql.node.AbstractNodeNoChildren;
import gov.va.transpiler.sparksql.utilities.CQLTypeToSparkSQLType;

public class LiteralNode extends AbstractNodeNoChildren {

    private final CQLTypeToSparkSQLType cqlTypeToSparkSQLType;

    private String resultType;

    public LiteralNode(CQLTypeToSparkSQLType cqlTypeToPythonType) {
        this.cqlTypeToSparkSQLType = cqlTypeToPythonType;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    @Override
    public String asOneLine() {
        return "SELECT " + cqlTypeToSparkSQLType.toSparkSQLType(getName(), cqlTypeToSparkSQLType.asSparkSQLType(getResultType())) + " " + SINGLE_VALUE_COLUMN_NAME;
    }
}
