package gov.va.transpiler.sparksql.node.ary;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hl7.elm.r1.Library;

import gov.va.transpiler.sparksql.node.AbstractCQLNode;
import gov.va.transpiler.sparksql.node.Ary;
import gov.va.transpiler.sparksql.node.OutputWriter;
import gov.va.transpiler.sparksql.node.leaf.UsingDefNode;
import gov.va.transpiler.sparksql.node.unary.FunctionDefNode;

public class LibraryNode extends Ary {

    private List<UsingDefNode> usingDefList = new ArrayList<>();
    /**
     * // TODO: support include statements
     */
    private List<LibraryNode> includedLibraries = new ArrayList<>();
    /**
     * We expand functions inline and therefore won't print them at the top level
     */
    private List<FunctionDefNode> functionDefinitions = new ArrayList<>();
    @Override
    public boolean addChild(AbstractCQLNode child) {
        if (child instanceof UsingDefNode) {
            usingDefList.add((UsingDefNode) child);
            return true;
        } else if (child instanceof FunctionDefNode) {
            functionDefinitions.add((FunctionDefNode) child);
            return true;
        } else if (child instanceof LibraryNode) {
            includedLibraries.add((LibraryNode) child);
            return true;
        }
        return super.addChild(child);
    }

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
        // Include other libraries
        // TODO: print the entire library inside the SQL file, or find a way to reference SQL files from other SQL files
        if (!includedLibraries.isEmpty()) {
            outputWriter.printFullLine("/* include statements from CQL");
            for (var includedLibrary : includedLibraries) {
                outputWriter.printFullLine("include " + includedLibrary.getName());
            }
            outputWriter.printFullLine("*/");
        }
        // Print function definitions
        // TODO: no more inline functions!
        if (!functionDefinitions.isEmpty()) {
            outputWriter.printFullLine("/* function definitions from CQL");
            for (var functionDefinitions : functionDefinitions) {
                outputWriter.printFullLine("include " + functionDefinitions.asOneLine());
            }
            outputWriter.printFullLine("*/");
        }
        // Print children
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
