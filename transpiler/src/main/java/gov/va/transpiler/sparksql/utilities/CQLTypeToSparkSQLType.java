package gov.va.transpiler.sparksql.utilities;

import java.util.LinkedHashMap;
import java.util.Map;

public class CQLTypeToSparkSQLType {

    public static enum SparkSQLLiteralType {
        String,
        Number
    }

    private final Map<String, SparkSQLLiteralType> cqlLiteralTypeMap;

    public CQLTypeToSparkSQLType() {
        cqlLiteralTypeMap = new LinkedHashMap<>();

        // set up cqlLiteralTypeMap
        cqlLiteralTypeMap.put("System.String", SparkSQLLiteralType.String);
        cqlLiteralTypeMap.put("System.Integer", SparkSQLLiteralType.Number);
    }

    public SparkSQLLiteralType asSparkSQLType(String cqlType) {
        return cqlLiteralTypeMap.get(cqlType);
    }

    public String toSparkSQLType(String value, SparkSQLLiteralType type) {
        switch (type) {
            case String:
                return "'" + value + "'";
            case Number:
            default:
                return value;
        }
    }
}
