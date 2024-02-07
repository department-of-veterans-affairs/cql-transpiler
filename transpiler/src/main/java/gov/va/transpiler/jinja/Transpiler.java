package gov.va.transpiler.jinja;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.hl7.elm.r1.Library;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.transpiler.jinja.converter.Converter;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.CQLFileContentRetriever;
import gov.va.transpiler.jinja.printing.SegmentPrinter;
import gov.va.transpiler.jinja.state.State;

public class Transpiler {

    public static void main(String[] args) throws IOException {
        String cql = ""
        + "library Retrievals version '1.0'\n"
        + "using QDM version '5.6'\n"
        + "context Unfiltered\n"
        + "define testLiteral: 1\n"
        + "define testReference: testLiteral\n"
        + "define testEmptyList: {}\n"
        + "define testList: {1, 2}\n"
        + "define testRetrieve: [\"Encounter, Performed\"]\n"
        + "define testTuple_withCollection: {foo: testRetrieve, bar: 1}\n"
        + "define testTupleReference: testTuple_withCollection.bar\n"
        + "define testTupleReference_decollecting: testTuple_withCollection.foo\n"
        + "define testNestedTuple: {baz: 1, foobar: testTuple_withCollection}\n"
        + "define testNestedTupleReference: testNestedTuple.foobar.bar\n"
        + "define testNestedTupleReferenceTable: testNestedTuple.foobar.foo\n"
        + "define function testFunction(val Integer):\n"
        + "    2 * val\n"
        + "define testFunctionReference: testFunction(1)"
        ;

        var fileLibrarySourceProvider = new FileLibrarySourceProvider("./resources/cql");
        var jinjaTarget = "jinja/";
        var compiler = new CqfCompiler(fileLibrarySourceProvider);

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
           segmentPrinter.toFiles(mapped.toSegment(), jinjaTarget);
        }
    }
}
