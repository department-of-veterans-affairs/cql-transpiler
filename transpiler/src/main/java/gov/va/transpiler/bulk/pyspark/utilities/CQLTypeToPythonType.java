package gov.va.transpiler.bulk.pyspark.utilities;

import java.util.LinkedHashMap;
import java.util.Map;

public class CQLTypeToPythonType {

    public static enum PythonLiteralType {
        String,
        Number
    }

    private final Map<String, PythonLiteralType> cqlLiteralTypeMap;

    public CQLTypeToPythonType() {
        cqlLiteralTypeMap = new LinkedHashMap<>();

        // set up cqlLiteralTypeMap
        cqlLiteralTypeMap.put("System.String", PythonLiteralType.String);
        cqlLiteralTypeMap.put("System.Integer", PythonLiteralType.Number);
    }

    public PythonLiteralType asPythonType(String cqlType) {
        return cqlLiteralTypeMap.get(cqlType);
    }

    public String toPythonRepresentation(String value, PythonLiteralType type) {
        switch (type) {
            case String:
                return "'" + value + "'";
            case Number:
            default:
                return value;
        }
    }
}
