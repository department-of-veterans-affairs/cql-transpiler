package gov.va.transpiler.bulk.pyspark.output;

import java.util.HashMap;
import java.util.LinkedHashMap;

import gov.va.transpiler.output.OutputNode;

public class ValueNode extends OutputNode {

    public static enum PYTHON_DATA_TYPE {
        Number,
        String,
        Variable
    }

    private static final HashMap<String, PYTHON_DATA_TYPE> dataTypeMappings = new LinkedHashMap<>();

    {
        // populate dateTypeMappings
        dataTypeMappings.put("System.Integer", PYTHON_DATA_TYPE.Number);
        dataTypeMappings.put("System.String", PYTHON_DATA_TYPE.String);
    }

    private String value;

    @Override
    public boolean addChild(OutputNode child) {
        return false;
    }

    @Override
    public String asOneLine() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toPythonRepresentation(String value, PYTHON_DATA_TYPE type) {
        // TODO: expand for all types
        switch (type) {
            case String:
                return "\"" + value + "\"";
            case Number:
            case Variable:
            default:
                return value;
        }
    }

    public PYTHON_DATA_TYPE toPythonDataType(String hl7FhirType) {
        return dataTypeMappings.get(hl7FhirType);
    }
}
