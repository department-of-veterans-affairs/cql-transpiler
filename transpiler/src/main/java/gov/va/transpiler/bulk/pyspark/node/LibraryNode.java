package gov.va.transpiler.bulk.pyspark.node;

import java.util.UUID;

import org.hl7.elm.r1.Library;

import gov.va.transpiler.bulk.pyspark.OutputWriter;
import gov.va.transpiler.node.ParentNode;

public class LibraryNode extends ParentNode {

    String version;

    public String getFileNameFromLibrary(Library library) {
        String className = null;
        var identifier = library.getIdentifier();
        if (identifier != null) {
            var libraryName = identifier.getId();
            var libraryVersion = identifier.getVersion();
            if (libraryName != null) {
                className = libraryName;
                if (libraryVersion != null) {
                    className += "_" + libraryVersion.replace('.', '_');
                    // Version numbers end in newlines for some reason
                    className = className.trim();
                }
            }
        }
        if (className == null) {
            className = generateAnonymousFileName();
        }
        return className;
    }

    private String generateAnonymousFileName() {
        return "AnonymousLibrary_" + UUID.randomUUID().toString().replace('-', '_');
    }

    @Override
    public String asOneLine() {
        return null;
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        var printSuccess = true;
        for (var child : getChildren()) {
            if (!child.print(outputWriter)) {
                printSuccess = false;
                var failureMessage = "# failed to print: [" + child.getClass().getName() + "@" + child.hashCode() + " / "+ child.asOneLine() +  "]";
                outputWriter.printFullLine(failureMessage);
            }
        }
        return printSuccess;
    }
}