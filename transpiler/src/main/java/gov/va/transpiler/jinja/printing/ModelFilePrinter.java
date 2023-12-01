package gov.va.transpiler.jinja.printing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.transpiler.jinja.node.trackable.element.LibraryNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.ExpressionDefNode;
import gov.va.transpiler.jinja.node.trackable.element.expressiondef.FunctionDefNode;
import gov.va.transpiler.jinja.standards.Standards;

public class ModelFilePrinter {

    public void printModels(Map<LibraryNode, Set<ExpressionDefNode<?>>> models, String targetFolder, CQLFileContentRetriever cqlFileContentRetriever, boolean printFunctions) throws IOException {
        for (var library: models.keySet()) {
            printModelsForLibrary(library, models.get(library), targetFolder, cqlFileContentRetriever, printFunctions);
        }
    }

    private void printModelsForLibrary(LibraryNode libraryNode, Set<ExpressionDefNode<?>> expressionDefNodeSet, String targetFolder, CQLFileContentRetriever cqlFileContentRetriever, boolean printFunctions) throws IOException {
        // create folder
        var libraryName = libraryNode.getTargetFileLocation();
        var libraryFolder = new File(targetFolder + Standards.GENERATED_INTERMEDIATE_MODEL_FOLDER + libraryName + Standards.FOLDER_SEPARATOR);

        if (libraryFolder.exists()) {
            libraryFolder.delete();
        }

        libraryFolder.mkdir();

        // create model files
        for (var expressionDefNode : expressionDefNodeSet) {
            if (printFunctions || !(expressionDefNode instanceof FunctionDefNode)) {
                printModelFile(cqlFileContentRetriever, expressionDefNode, libraryNode, targetFolder, libraryName, true);
            }
        }
    }

    private void printModelFile(CQLFileContentRetriever cqlFileContentRetriever, ExpressionDefNode<?> expressionDefNode, LibraryNode libraryNode, String targetFolder, String libraryName, boolean printFunctionDefContents) throws IOException {
        var modelName = expressionDefNode.sanitizeNameForJinja(expressionDefNode.referenceName());
        var expressionFile = new File(targetFolder + Standards.GENERATED_INTERMEDIATE_MODEL_FOLDER + libraryName + Standards.FOLDER_SEPARATOR + modelName + Standards.JINJA_FILE_POSTFIX);

        if (expressionFile.exists()) {
            expressionFile.delete();
        }

        expressionFile.createNewFile();

        printEquivalentCQLToModelFile(expressionFile, libraryNode.getCqlEquivalent().getIdentifier(), Locator.fromString(expressionDefNode.getCqlEquivalent().getLocator()), cqlFileContentRetriever);
        if (printFunctionDefContents || !(expressionDefNode instanceof FunctionDefNode)) {
            printExpressionDefToModelFile(expressionFile, libraryName, modelName);
        }
    }

    private void printEquivalentCQLToModelFile(File file, VersionedIdentifier versionedLibraryIdentifier, Locator locator, CQLFileContentRetriever cqlFileContentRetriever) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            outputStream.write("{# original CQL begins -#}\n/*\n".getBytes());
            var linesOfTextFromLibrary = cqlFileContentRetriever.getLinesOfTextFromLibrary(versionedLibraryIdentifier, locator);
            for (var line : linesOfTextFromLibrary) {
                outputStream.write(line.getBytes());
                outputStream.write("\n".getBytes());
            }
            outputStream.write("*/\n\n{# original CQL Ends #}\n".getBytes());
        }
    }

    private void printExpressionDefToModelFile(File file, String libraryName, String modelName) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            // prepare imports
            var initImport = "{%- from \"" + Standards.CUSTOM_OVERRIDES_FOLDER + "InitCustomOverrides.sql\" import initEnvironmentWithCustomOverrides %}\n";
            outputStream.write(initImport.getBytes());
            var modelImport = "{%- from \"" + Standards.GENERATED_INTERMEDIATE_AST_FOLDER + libraryName + Standards.JINJA_FILE_POSTFIX + "\" import " + modelName + " %}\n";
            outputStream.write(modelImport.getBytes());

            outputStream.write("\n".getBytes());

            // prepare environment
            outputStream.write("{%- set environment = namespace() %}\n".getBytes());
            outputStream.write("{%- do initEnvironmentWithCustomOverrides(environment) -%}\n".getBytes());
            outputStream.write("\n".getBytes());

            // invoke the intermediate AST parsing logic by calling the requisite model
            outputStream.write("{{ ".getBytes());
            outputStream.write(modelName.getBytes());
            outputStream.write("(environment, none) }}".getBytes());
        }
    }
}
