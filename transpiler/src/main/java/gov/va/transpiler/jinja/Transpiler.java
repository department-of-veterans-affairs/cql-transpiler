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

public class Transpiler {

    public static void main(String[] args) throws IOException {
        var compiler = new CqfCompiler(new FileLibrarySourceProvider("./src/test/resources/cql"));
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

        for (var mapped : convertedLibraries) {
            mapped.toSegment().toFiles("./");
        }
    }
}
