package gov.va.transpiler.sparksql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import org.hl7.elm.r1.Library;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.transpiler.sparksql.converter.Converter;
import gov.va.transpiler.sparksql.converter.State;
import gov.va.transpiler.sparksql.node.OutputWriter;
import gov.va.transpiler.sparksql.utilities.CQLNameToSparkSQLName;
import gov.va.transpiler.sparksql.utilities.CQLTypeToSparkSQLType;

public class Transpiler {

    public static void main(String[] args) {
        String fullPathToResources = args[0];
        String fullPathToFile = args[1];

        var compiler = new CqfCompiler(new FileLibrarySourceProvider(fullPathToResources));

        try (BufferedReader br = new BufferedReader(new FileReader(fullPathToFile))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            var fileContent = stringBuilder.toString();
            var libraryList = compiler.compile(fileContent);

            // Reverse the order of library processing, so dependencies are processed before the scripts that depend on them
            Collections.reverse(libraryList);
            // Transform the AST into SparkSQL Scripts
            var cqlTypeToSparkSQLType = new CQLTypeToSparkSQLType();
            var cqlNameToSparkSQLName = new CQLNameToSparkSQLName();
            var elmToSparkSQLConverter = new Converter(cqlTypeToSparkSQLType, cqlNameToSparkSQLName);        
            var elmToSparkSQLConverterState = new State();
            var convertedLibraries = new ArrayList<String>();
            for (Library library : libraryList) {
                var outputNode = elmToSparkSQLConverter.convert(library, elmToSparkSQLConverterState);
                var pySparkOutputWriter = new OutputWriter(0, "    ", "\n");
                outputNode.print(pySparkOutputWriter);
                convertedLibraries.add(pySparkOutputWriter.getDocumentContents());
            }

            int count = 1;
            for (var lib : convertedLibraries) {
                System.out.println();
                System.out.println("[[[Printing Library " + count++ + " of " + convertedLibraries.size() + "]]]");
                System.out.println();
                System.out.print(lib);
            }
        } catch (Exception e) {
            // do nothing
        }
    }
}
