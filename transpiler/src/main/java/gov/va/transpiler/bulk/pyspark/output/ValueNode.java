package gov.va.transpiler.bulk.pyspark.output;

import java.util.HashMap;
import java.util.LinkedHashMap;

import gov.va.transpiler.output.OutputNode;

public class ValueNode extends OutputNode {

    public static enum PYTHON_DATA_TYPE {
        Number,
        String
    }

    private static final HashMap<String, PYTHON_DATA_TYPE> dataTypeMappings = new LinkedHashMap<>();

    {
        // populate dateTypeMappings
        dataTypeMappings.put("System.Integer", PYTHON_DATA_TYPE.Number);
        dataTypeMappings.put("System.String", PYTHON_DATA_TYPE.String);
    }

    private String value;
    private PYTHON_DATA_TYPE type;

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

    private String toPythonRepresentation(String value, PYTHON_DATA_TYPE type) {
        // TODO: expand for all types
        switch (type) {
            case String:
                return "\"" + value + "\"";
            case Number:
            default:
                return value;
        }
    }

    public void setPythonDataType(PYTHON_DATA_TYPE type) {
        this.type = type;
    }

    public static PYTHON_DATA_TYPE getMatchingPythonDataType(String hl7FhirType) {
        return dataTypeMappings.get(hl7FhirType);
    }
}
