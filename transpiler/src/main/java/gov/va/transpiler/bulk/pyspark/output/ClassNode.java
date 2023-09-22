package gov.va.transpiler.bulk.pyspark.output;

import java.util.UUID;

import org.hl7.elm.r1.Library;

import gov.va.transpiler.output.OutputWriter;

public class ClassNode extends MultiChildNode {

    String name;
    String version;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassnameFromLibrary(Library library) {
        String className = null;
        var identifier = library.getIdentifier();
        if (identifier != null) {
            String libraryName = identifier.getId();
            String libraryVersion = identifier.getVersion();
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
            className = generateAnonymousLibraryName();
        }
        return className;
    }

    private String generateAnonymousLibraryName() {
        return "AnonymousLibrary_" + UUID.randomUUID().toString().replace('-', '_');
    }
    @Override
    public String asOneLine() {
        return null;
    }

    @Override
    public boolean print(OutputWriter outputWriter) {
        String classHeader = "class " + getName() + ":";
        outputWriter.addLine(classHeader);
        outputWriter.raiseIndentLevel();
        boolean printSuccess = true;
        for (var child : getChildren()) {
            if (!child.print(outputWriter)) {
                printSuccess = false;
                outputWriter.addLine("# failed to print: [" + child.getClass().getName() + "@" + child.hashCode() + " / "+ child.asOneLine() +  "]");
            }
        }
        outputWriter.lowerIndentLevel();
        return printSuccess;
    }
}
