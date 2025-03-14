package gov.va.transpiler.jinja;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hl7.elm.r1.Library;

import gov.va.compiler.CqfCompiler;
import gov.va.compiler.FileLibrarySourceProvider;
import gov.va.transpiler.jinja.converter.Converter;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.CQLFileContentRetriever;
import gov.va.transpiler.jinja.printing.ModelFilePrinter;
import gov.va.transpiler.jinja.printing.SegmentPrinter;
import gov.va.transpiler.jinja.standards.Standards;
import gov.va.transpiler.jinja.state.State;

public class Transpiler {

    public static void main(String[] args) throws IOException {
        Map<String, String> arguments = parseArguments(args);
        var librarySource = arguments.get("librarySource");
        var jinjaTarget = arguments.get("jinjaTarget");
        var modelOrderFilePath = arguments.getOrDefault("modelOrderFilePath", jinjaTarget + "model_order.txt");
        var targetLanguage = arguments.getOrDefault("targetLanguage", "sparksql");
        var printFunctions = Boolean.parseBoolean(arguments.getOrDefault("printFunctions", "false"));
        
        var fileLibrarySourceProvider = new FileLibrarySourceProvider(librarySource);
        var compiler = new CqfCompiler(fileLibrarySourceProvider);
        Standards.setTargetLanguage(targetLanguage);

        // Initialize the ModelFilePrinter with the model_order.txt file
        var modelFilePrinter = new ModelFilePrinter(modelOrderFilePath);

        try {
            // read the contents of the file to text
            String[] cqlLibrariesToTranspile = {"CMS104-v12-0-000-QDM-5-6.cql", "CMS506v7/CMS506-v7-0-000-QDM-5-6.cql"};
            for (var cqlLibraryToTranspile: cqlLibrariesToTranspile) {
                    String cqlLibraryToTranspileAsText = Files.readString(Paths.get(librarySource + cqlLibraryToTranspile));
            
                    // Compile CQL text files into a CQL AST in memory
                    var libraryList = compiler.compile(cqlLibraryToTranspileAsText);
            
                    // Transforms the CQL AST into an intermediate AST that keeps track of all information needed to generate SQL ASTs
                    var converter = new Converter();        
                    var state = new State();
                    var convertedLibraries = new ArrayList<TranspilerNode>();
                    for (Library library : libraryList) {
                        var outputNode = converter.convert(library, state);
                        convertedLibraries.add(outputNode);
                    }
                    var cqlFileContentRetriever = new CQLFileContentRetriever(fileLibrarySourceProvider, cqlLibraryToTranspileAsText);
            
                    // Renders the intermediate AST as a set of Jinja files
                    var segmentPrinter = new SegmentPrinter(cqlFileContentRetriever);
                    for (var mapped : convertedLibraries) {
                       segmentPrinter.toFiles(mapped.toSegment(), jinjaTarget + Standards.GENERATED_INTERMEDIATE_AST_FOLDER);
                    }
            
                    // Creates model files for the intermediate ASTs
                    modelFilePrinter.printModels(state.getModelTracking(), jinjaTarget, cqlFileContentRetriever, printFunctions);
            }
        } finally {
            // Close the modelOrderWriter
            modelFilePrinter.closeModelOrderWriter();
        }
    }

    private static Map<String, String> parseArguments(String[] args) {
        Map<String, String> arguments = new HashMap<>();
        for (String arg : args) {
            String[] keyValue = arg.split("=");
            if (keyValue.length == 2) {
                arguments.put(keyValue[0], keyValue[1]);
            }
        }
        return arguments;
    }
}
