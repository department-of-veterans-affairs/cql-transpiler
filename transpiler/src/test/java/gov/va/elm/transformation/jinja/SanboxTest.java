package gov.va.elm.transformation.jinja;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.cqframework.cql.cql2elm.LibrarySourceProvider;
import org.hl7.elm.r1.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.transpiler.jinja.converter.Converter;
import gov.va.transpiler.jinja.converter.State;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.CQLFileContentRetriever;
import gov.va.transpiler.jinja.printing.SegmentPrinter;

public class SanboxTest {

    private LibrarySourceProvider provider;
    private CqfCompiler compiler;

    @BeforeEach
    public void setup() {
        provider = new FileLibrarySourceProvider("./src/test/resources/cql");
        compiler = new CqfCompiler(provider);
    }

    @Test
    public void testExpressionDefLiteral() throws IOException {
        String cql = ""
            + "define myconst_1: 123\n"
            ;

        var convertedLibraries = processCQLToJinja(cql);
        var retriever = new CQLFileContentRetriever(provider, cql);
        var printer = new SegmentPrinter(retriever);

        for (var mapped : convertedLibraries) {
            printer.toFiles(mapped.toSegment(), "./");
        }
    }

    private List<TranspilerNode> processCQLToJinja(String cql) {
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

        return convertedLibraries;
    }
}
