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
        + "include MATGlobalCommonFunctions version '7.0.000' called Global\n"
        + "valueset \"Nonelective Inpatient Encounter\": 'urn:oid:2.16.840.1.113883.3.117.1.7.1.424'\n"
        + "parameter \"Measurement Period\" Interval<DateTime>\n"
        + "define \"Non Elective Inpatient Encounter\":\n"
        + "  [\"Encounter, Performed\": \"Nonelective Inpatient Encounter\"] NonElectiveEncounter\n"
        + "    where Global.\"LengthInDays\" ( NonElectiveEncounter.relevantPeriod ) <= 120\n"
        + "      and NonElectiveEncounter.relevantPeriod ends during day of \"Measurement Period\"\n"
       ;

        var fileLibrarySourceProvider = new FileLibrarySourceProvider("./resources/cql");
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
           segmentPrinter.toFiles(mapped.toSegment(), "./");
        }
    }
}
