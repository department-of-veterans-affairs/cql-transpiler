package gov.va.transpiler.sparksql.node.leaf;

import gov.va.transpiler.sparksql.node.Leaf;
import gov.va.transpiler.sparksql.utilities.CQLTypeToSparkSQLType;

public class LiteralNode extends Leaf {

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
        return cqlTypeToSparkSQLType.toSparkSQLType(getName(), cqlTypeToSparkSQLType.asSparkSQLType(getResultType()));
    }
}
