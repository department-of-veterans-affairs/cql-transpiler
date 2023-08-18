package gov.va.sparkcql.service.compiler;

import java.util.List;

import org.hl7.cql_annotations.r1.CqlToElmError;
import org.hl7.elm.r1.Library;

import gov.va.sparkcql.log.ConsoleColors;

public class CqlErrorChecker {

    private List<CqlToElmError> errors;

    public CqlErrorChecker(Library elm) {
        this.errors = elm.getAnnotation().stream()
                .filter(a -> a instanceof CqlToElmError)
                .map(a -> (CqlToElmError) a).toList();
    }

    public String toPrettyString() {
        var formattedErrorList = this.errors.stream().map(e -> {
            return String.format(
                    "Library '" + ConsoleColors.BRIGHT_WHITE + "%s" + ConsoleColors.RESET + "' version %s"
                            + ", line %s: "
                            + ConsoleColors.BRIGHT_RED + "%s" + ConsoleColors.RESET,
                    e.getLibraryId(),
                    e.getLibraryVersion(),
                    e.getStartLine(),
                    e.getMessage());
        }).toList();

        return String.join("\r\n", formattedErrorList);
    }

    public List<CqlToElmError> getErrors() {
        return this.errors;
    }

    public Boolean hasErrors() {
        return this.errors.size() > 0;
    }
}