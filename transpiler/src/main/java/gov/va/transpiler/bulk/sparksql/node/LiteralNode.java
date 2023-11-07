package gov.va.transpiler.bulk.sparksql.node;

import gov.va.transpiler.bulk.sparksql.utilities.CQLTypeToSparkSQLType;

import static gov.va.transpiler.bulk.sparksql.utilities.Standards.SINGLE_VALUE_COLUMN_NAME;

import org.hl7.elm.r1.Literal;

public class LiteralNode extends AbstractNodeNoChildren<Literal> {

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
