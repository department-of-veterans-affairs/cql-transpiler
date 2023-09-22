package gov.va.transpiler.bulk.pyspark.output;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            case Variable:
                return variableToPythonVariable(value);
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

    public String variableToPythonVariable(String variableName) {
        String toReturn = variableName;
        // Match on any characters that are legal for variable names in CQL but illegal in Python
        String illegalCharacters = " \\\"";
        Pattern pattern = Pattern.compile("[" + illegalCharacters + "]");
        Matcher matcher = pattern.matcher(variableName);
        if (matcher.find()) {
            // Replace all illegal characters with '_'
            for (var illegalCharacter : illegalCharacters.toCharArray()) {
                toReturn = toReturn.replace(illegalCharacter, '_');
            }
            // Append the hash of the variable name to make sure the variable name remains unique
            toReturn += variableName.hashCode();
        }
        return toReturn;
    }
}
