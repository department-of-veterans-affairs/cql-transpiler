package gov.va.transpiler.bulk.sparksql.node;

import java.util.UUID;

import org.hl7.elm.r1.Library;

import gov.va.transpiler.node.OutputWriter;

public class LibraryNode extends AbstractNodeWithChildren {

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
        outputWriter.printFullLine("-- Library: " + getName());
        var printSuccess = true;
        for (var child : getChildren()) {
            if (child.print(outputWriter)) {
                outputWriter.addText(";");
            } else {
                printSuccess = false;
                var failureMessage = "# failed to print: [" + child.getClass().getName() + "@" + child.hashCode() + " / "+ child.asOneLine() +  "]";
                outputWriter.printFullLine(failureMessage);
            }
            outputWriter.endLine();
        }
        return printSuccess;
    }
}
