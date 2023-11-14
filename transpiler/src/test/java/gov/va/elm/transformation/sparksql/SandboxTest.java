package gov.va.elm.transformation.sparksql;

import static gov.va.transpiler.sparksql.utilities.Standards.DEFAULT_CQL_DATE_TIME;

import java.util.ArrayList;
import java.util.List;

import org.hl7.elm.r1.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.transpiler.sparksql.converter.Converter;
import gov.va.transpiler.sparksql.converter.State;
import gov.va.transpiler.sparksql.node.OutputWriter;
import gov.va.transpiler.sparksql.utilities.CQLNameToSparkSQLName;
import gov.va.transpiler.sparksql.utilities.CQLTypeToSparkSQLType;

public class SandboxTest {

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

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testExpressionRef() {
        String cql = ""
            + "define myconst_1: 123\n"
            + "define myconst_2: myconst_1\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testList() {
        String cql = ""
            + "define myconst_1: 1\n"
            + "define myconst_2: {}\n"
            + "define myconst_3: { myconst_1 }\n"
            + "define myconst_4: { 1, myconst_1 }\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testLibrary() {
        String cql = ""
            + "library Retrievals version '1.0'\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
        // TODO: libraries saved as files?
        throw new RuntimeException();
    }

    @Test
    public void testRetrieve() {
        String cql = ""
            + "using FHIR version '4.0.1'\n"
            + "define var: [Encounter]\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testRetrieveCompressed() {
        String cql = ""
            + "using FHIR version '4.0.1'\n"
            + "define var: {[Encounter]}\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testRetrieveReferenceCompressed() {
        String cql = ""
            + "using FHIR version '4.0.1'\n"
            + "define a: [Encounter]\n"
            + "define b: {a}\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testTuple() {
        String cql = ""
            + "using FHIR version '4.0.1'\n"
            + "define a: {foo: 1, bar: [Encounter]}\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testSimplePropertyOfSimpleTuple() {
        String cql = ""
            + "using FHIR version '4.0.1'\n"
            + "define a: {foo: 1, bar: 2}\n"
            + "define b: a.foo\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testSimplePropertyOfComplexTuple() {
        String cql = ""
            + "using FHIR version '4.0.1'\n"
            + "define a: {foo: [Encounter], bar: 2}\n"
            + "define b: a.foo\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testComplexPropertyOfComplexTuple() {
        String cql = ""
            + "using FHIR version '4.0.1'\n"
            + "define a: {foo: {alpha: [Encounter]}, bar: 2}\n"
            + "define b: a.foo.alpha\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testInclude() {
        String cql = ""
            + "library Retrievals version '1.0'\n"
            + "include FHIRHelpers version '4.1.000'\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
        // TODO: 'include' causes the entire FHIRHelpers library to be imported, and then requires that it be supported
        throw new RuntimeException();
    }

    @Test
    public void testDateTime() {
        String cql = ""
            + "library Retrievals version '1.0'\n"
            + "using QUICK\n"
            + "define testdate: " + DEFAULT_CQL_DATE_TIME + "\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
        // Dates are a MASSIVE can of worms. I'm just not going to worry about them for now.
        throw new RuntimeException();
    }

    @Test
    public void testConcatenateString() {
        String cql = ""
            + "define a: '1' + '2'\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testLiteralMath() {
        String cql = ""
            + "define a: 2 + 3\n"
            + "define b: 2 - 3\n"
            + "define c: 2 / 3\n"
            + "define d: 2 * 3\n"
            + "define e: -1\n"
            + "define f: (1 + 2 / -3 + 4) * 5\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testMathWithReference() {
        String cql = ""
            + "define a: 2 + 3\n"
            + "define b: a - 3\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testEqual() {
        String cql = ""
            + "define a: 1 = 2\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }


    @Test
    public void testQueryWithSimpleReturn() {
        String cql = ""
            + "using FHIR version '4.0.1'\n"
            + "define a: [Encounter] E return E.period\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testQueryWithWhereClause() {
        String cql = ""
            + "library Retrievals version '1.0'\n"
            + "using QUICK\n"
            + "define a: [Encounter] E where E.period ends after " + DEFAULT_CQL_DATE_TIME + "\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testPropertyDuringRetrieve() {
        String cql = ""
            + "library Retrievals version '1.0'\n"
            + "using QUICK\n"
            + "define a: [Encounter] E return E.status.value\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testEqualDuringRetrieve() {
        String cql = ""
            + "library Retrievals version '1.0'\n"
            + "using QUICK\n"
            + "define a: [Encounter] E where E.status.value = 'finished'\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testContextDuringCount() {
        String cql = ""
            + "library Retrievals version '1.0'\n"
            + "using QUICK\n"
            + "context Patient\n"
            + "define a: Count([Encounter])\n"
            + "context Unfiltered\n"
            + "define b: Count([Encounter])\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    @Test
    public void testUnion() {
        String cql = ""
            + "library Retrievals version '1.0'\n"
            + "using QUICK\n"
            + "define a: [Encounter] union [Patient]\n"
            ;

        var sparksql = processCQLToSparkSQL(cql);
        for (String output : sparksql) {
            System.out.println(output);
        }
    }

    private List<String> processCQLToSparkSQL(String cql) {
        var libraryList = compiler.compile(cql);

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

        return convertedLibraries;
    }
}
