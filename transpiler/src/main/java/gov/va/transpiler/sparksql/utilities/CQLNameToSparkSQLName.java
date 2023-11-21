package gov.va.transpiler.sparksql.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CQLNameToSparkSQLName {

    public String convertNameToSafe(String variableName) {
        // TODO
        return variableName.replace(" ", "_");
    }

    public String convertNameToUnique(String variableName) {
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
            toReturn += Math.abs(variableName.hashCode());
        }
        return toReturn;
    }
}
