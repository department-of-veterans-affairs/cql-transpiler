package gov.va.elm.transformation.bulk.sparksql;

import java.util.ArrayList;
import java.util.List;

import org.hl7.elm.r1.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.transpiler.bulk.sparksql.BulkElmToSparkSQLConverter;
import gov.va.transpiler.bulk.sparksql.BulkElmToSparkSQLConverterState;
import gov.va.transpiler.bulk.sparksql.SparkSQLOutputWriter;
import gov.va.transpiler.bulk.sparksql.utilities.CQLNameToSparkSQLName;
import gov.va.transpiler.bulk.sparksql.utilities.CQLTypeToSparkSQLType;

public class SandboxTest {

    private CqfCompiler compiler;

    @BeforeEach
    public void setup() {
        compiler = new CqfCompiler(new FileLibrarySourceProvider("./src/test/resources/cql"));
    }

    @Test
    public void testExpressionDefLiteral() {
        String cql = ""
            //+ "library TestCQL version '2.1\n'"
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
            //+ "library TestCQL version '2.1\n'"
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
            //+ "library TestCQL version '2.1\n'"
            + "define myconst_1: 1\n"
            + "define myconst_2: {}"
            + "define myconst_3: { myconst_1 }"
            + "define myconst_4: { 1, myconst_1 }"
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
        var elmToSparkSQLConverter = new BulkElmToSparkSQLConverter(cqlTypeToSparkSQLType, cqlNameToSparkSQLName);        
        var elmToSparkSQLConverterState = new BulkElmToSparkSQLConverterState();
        var convertedLibraries = new ArrayList<String>();
        for (Library library : libraryList) {
            var outputNode = elmToSparkSQLConverter.convert(library, elmToSparkSQLConverterState);
            var pySparkOutputWriter = new SparkSQLOutputWriter(0, "    ", "\n");
            outputNode.print(pySparkOutputWriter);
            convertedLibraries.add(pySparkOutputWriter.getDocumentContents());
        }

        return convertedLibraries;
    }
}
