package gov.va.transpiler.bulk.pyspark.output;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gov.va.transpiler.output.OutputNode;

public class VariableNameNode extends OutputNode{

    public VariableNameNode(String variableName) {
        setCqlNodeEquivalent(variableName);
    }

    private String variableNameToPythonVariableName(String variableName) {
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
            // Make sure the value is positive because Python doesn't support hyphens in variable names
            // Hash codes are deterministic for Strings, so running this more than once on the same variable name will produce the same results
            toReturn += variableName.hashCode() * variableName.hashCode();
        }
        return toReturn;
    }

    @Override
    public boolean addChild(OutputNode child) {
        return false;
    }

    @Override
    public String asOneLine() {
        return variableNameToPythonVariableName((String) getCqlNodeEquivalent());
    }
}
