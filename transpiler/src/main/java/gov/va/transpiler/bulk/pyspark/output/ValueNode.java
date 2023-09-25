package gov.va.transpiler.bulk.pyspark.output;

import java.util.HashMap;
import java.util.LinkedHashMap;

import gov.va.transpiler.output.OutputNode;

public class ValueNode extends OutputNode {

    public static enum PythonDataType {
        Number,
        String
    }

    private static final HashMap<String, PythonDataType> dataTypeMappings = new LinkedHashMap<>();

    {
        // populate dateTypeMappings
        dataTypeMappings.put("System.Integer", PythonDataType.Number);
        dataTypeMappings.put("System.String", PythonDataType.String);
    }

    private String value;
    private PythonDataType type;

    @Override
    public boolean addChild(OutputNode child) {
        return false;
    }

    @Override
    public String asOneLine() {
        return toPythonRepresentation(value, type);
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String toPythonRepresentation(String value, PythonDataType type) {
        // TODO: expand for all types
        switch (type) {
            case String:
                return "\"" + value + "\"";
            case Number:
            default:
                return value;
        }
    }

    public void setPythonDataType(PythonDataType type) {
        this.type = type;
    }

    public static PythonDataType getMatchingPythonDataType(String hl7FhirType) {
        return dataTypeMappings.get(hl7FhirType);
    }
}
