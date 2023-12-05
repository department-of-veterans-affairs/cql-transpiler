package gov.va.elm.transformation.jinja;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hl7.elm.r1.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.transpiler.jinja.converter.Converter;
import gov.va.transpiler.jinja.converter.State;
import gov.va.transpiler.jinja.node.TranspilerNode;
import gov.va.transpiler.jinja.printing.Segment;

public class SanboxTest {

    private CqfCompiler compiler;

    @BeforeEach
    public void setup() {
        compiler = new CqfCompiler(new FileLibrarySourceProvider("./src/test/resources/cql"));
    }

    @Test
    public void testExpressionDefLiteral() {
        String cql = ""
            + "define myconst_1: 123\n"
            ;

        processCQLToJinja(cql).stream().map(TranspilerNode::toSegment).map(Segment::toString).forEach(
            libraryAsString -> {
                System.out.print(libraryAsString);
            }
        );
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
