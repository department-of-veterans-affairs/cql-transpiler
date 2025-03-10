package gov.va.compiler;

import java.util.List;
import java.util.stream.Collectors;

import org.hl7.cql_annotations.r1.CqlToElmError;
import org.hl7.elm.r1.Library;

public class CqlErrorChecker {

    public static String RESET = "\u001B[0m";
    public static String BRIGHT_WHITE = "\u001B[97m";
    public static String BRIGHT_RED = "\u001B[91m";

    private List<CqlToElmError> errors;

    public CqlErrorChecker(Library elm) {
        this.errors = elm.getAnnotation().stream()
                .filter(a -> a instanceof CqlToElmError)
                .map(a -> (CqlToElmError) a).collect(Collectors.toList());
    }

    public String toPrettyString() {
        var formattedErrorList = this.errors.stream().map(e -> {
            return String.format(
                    "Library '" + BRIGHT_WHITE + "%s" + RESET + "' version %s"
                            + ", line %s: "
                            + BRIGHT_RED + "%s" + RESET,
                    e.getLibraryId(),
                    e.getLibraryVersion(),
                    e.getStartLine(),
                    e.getMessage());
        }).collect(Collectors.toList());

        return String.join("\r\n", formattedErrorList);
    }

    public List<CqlToElmError> getErrors() {
        return this.errors;
    }

    public Boolean hasErrors() {
        return !this.errors.isEmpty();
    }
}