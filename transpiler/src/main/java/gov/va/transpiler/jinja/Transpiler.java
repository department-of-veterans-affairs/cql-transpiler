package gov.va.transpiler.jinja;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.hl7.elm.r1.Library;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.transpiler.jinja.converter.Converter;
import gov.va.transpiler.jinja.converter.State;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.CQLFileContentRetriever;
import gov.va.transpiler.jinja.printing.SegmentPrinter;

public class Transpiler {

    public static void main(String[] args) throws IOException {
        var fileLibrarySourceProvider = new FileLibrarySourceProvider("./src/test/resources/cql");
        var compiler = new CqfCompiler(fileLibrarySourceProvider);
        String cql = ""
            + "define myconst_1: 123\n"
            + "define myconst_2: myconst_1\n"
            ;

        var libraryList = compiler.compile(cql);
        // Reverse the order of library processing, so dependencies are processed before the scripts that depend on them
        Collections.reverse(libraryList);

        // Transform the AST into Jinja Scripts
        var converter = new Converter();        
        var state = new State();
        var convertedLibraries = new ArrayList<TranspilerNode>();
        for (Library library : libraryList) {
            var outputNode = converter.convert(library, state);
            convertedLibraries.add(outputNode);
        }
        var cqlFileContentRetriever = new CQLFileContentRetriever(fileLibrarySourceProvider, cql);

        var segmentPrinter = new SegmentPrinter(cqlFileContentRetriever);
        for (var mapped : convertedLibraries) {
           segmentPrinter.toFiles(mapped.toSegment(), "temp/");
        }
    }
}
