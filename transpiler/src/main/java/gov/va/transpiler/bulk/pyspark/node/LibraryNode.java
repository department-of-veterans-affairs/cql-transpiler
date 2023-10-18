package gov.va.transpiler.bulk.pyspark.node;

import java.util.UUID;

import org.hl7.elm.r1.Library;

import gov.va.transpiler.node.OutputWriter;
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
        printPreface(outputWriter);

        var printSuccess = true;
        for (var child : getChildren()) {
            if (!child.print(outputWriter)) {
                printSuccess = false;
                var failureMessage = "# failed to print: [" + child.getClass().getName() + "@" + child.hashCode() + " / "+ child.asOneLine() +  "]";
                outputWriter.printFullLine(failureMessage);
            }
            outputWriter.endLine();
        }
        return printSuccess;
    }

    private void printPreface(OutputWriter outputWriter) {
        outputWriter.addText("" +
            "from pyspark.sql import DataFrame\n" + //
            "from pyspark.sql import SparkSession\n" + //
            "from user_provided_data import UserProvidedData\n" + //
            "from model.encounter import Encounter\n" + //
            "from model.patient import Patient\n" + //
            "from model.model_info import ModelInfo\n" + //
            "\n" + //
            "# always present\n" + //
            "def models(modelSource: str) -> dict[str, ModelInfo]:\n" + //
            "    # TODO: populate models based off a source, e.g. FHIR 4.0.1,\n" + //
            "    # TODO: models and model names should be generates/accessed based off imported model info files\n" + //
            "    return {'Encounter': Encounter(), 'Patient': Patient()}\n" + //
            "\n" + //
            "# always present\n" + //
            "def retrieve (spark: SparkSession, model: ModelInfo):\n" + //
            "    return spark.table(model.getName())\n" + //
            "\n" + //
            "# always present\n" + //
            "def applyContext(spark: SparkSession, dataFrame: DataFrame, context: ModelInfo):\n" + //
            "    if (context != None): \n" + //
            "        return dataFrame.join(retrieve(spark, context), on = context.getIdColumnName())\n" + //
            "    return dataFrame\n" + //
            "\n" + //
            "# always present\n" + //
            "def filterContext(userData: UserProvidedData, dataFrame: DataFrame, context: ModelInfo):\n" + //
            "    if (context != None): \n" + //
            "        return dataFrame.filter(dataFrame[context.getIdColumnName()] == userData.getModelContextID(context.getName()))\n" + //
            "    return dataFrame\n" + //
            "\n"
        );
        outputWriter.endLine();
    }
}
