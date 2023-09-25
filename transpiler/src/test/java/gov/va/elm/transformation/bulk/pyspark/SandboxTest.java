package gov.va.elm.transformation.bulk.pyspark;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hl7.elm.r1.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.transpiler.ElmTransformerState;
import gov.va.transpiler.bulk.BulkTransformationLoader;
import gov.va.transpiler.bulk.pyspark.BulkElmToPySparkConverter;
import gov.va.transpiler.bulk.pyspark.BulkElmToPySparkConverterState;
import gov.va.transpiler.bulk.pyspark.output.PySparkOutputWriter;
import gov.va.transpiler.bulk.transformations.ModifyLiterals;
import gov.va.transpiler.transformer.ElmTransformerRecursive;

public class SandboxTest {

    private CqfCompiler compiler;

    @BeforeEach
    public void setup() {
        compiler = new CqfCompiler(new FileLibrarySourceProvider("./src/test/resources/cql"));
    }

    @Test
    public void testDefineConstants() {
        String cql = ""
            //+ "library TestCQL version '2.1\n'"
            + "define myconst_1: 123\n"
            + "define myconst_2: myconst_1\n"
            + "define myconst_3: myconst_2 + 1\n"
            + "define myconst_4: 'abc'\n"
            ;

        var pyspark = processCQLToPySpark(cql);
        for (String output : pyspark) {
            System.out.println(output);
        }
        assertEquals(1, pyspark.size());
        String[] lines = pyspark.get(0).split("\n");
        assertEquals(6, lines.length);
        assertTrue(lines[0].contains("class AnonymousLibrary_"));
        assertEquals("    # Unsupported node UsingDef [  ]", lines[1]);
        assertEquals("    myconst_1 = 1231111111", lines[2]);
        assertEquals("    myconst_2 = myconst_1", lines[3]);
        assertEquals("    myconst_3 = myconst_2 + 1111111111", lines[4]);
        assertEquals("    myconst_4 = \"abc1111111\"", lines[5]);
    }

    @Test
    public void testDefineTuple() {
        // TODO: tuple to python structfield or spark.withcolumn
        String cql = ""
            + "library TestCQL version '2.1\n'"
            + "define \"My Tuple\": Tuple { a: 1, b: 'foo' }\n"
            + "define myconst: \"My Tuple\".a"
            ;

        var pyspark = processCQLToPySpark(cql);
        for (String output : pyspark) {
            System.out.println(output);
        }
        assertEquals(1, pyspark.size());
        String[] lines = pyspark.get(0).split("\n");
        assertEquals(4, lines.length);
        assertEquals("class TestCQL_2_1:", lines[0]);
        assertEquals("    # Unsupported node UsingDef [  ]", lines[1]);
        assertEquals("    My_Tuple240974736 = {'a' : 1, 'b' : \"foo\"}", lines[2]);
        assertEquals("    myconst = My_Tuple240974736['a']", lines[3]);
    }

    private List<String> processCQLToPySpark(String cql) {
        var libraryList = compiler.compile(cql);

        // Transform the CQL ELM tree into a more abstract version designed to be converted into data-based rather than patient-based semantics (the "bulk" elm tree)
        BulkTransformationLoader bulkTransformationLoader = new BulkTransformationLoader();
        bulkTransformationLoader.registerTransformations(Set.of(new ModifyLiterals()));
        ElmTransformerRecursive elmTransformerRecursive = new ElmTransformerRecursive(bulkTransformationLoader);
        for (Library library : libraryList) {
            // Create the state of the for the transformation transversal
            var elmTransformerState = new ElmTransformerState();
            // Setting the depth to -1 means the TransformationState will transverse to the bottom of the tree
            elmTransformerState.enterSubtreeSettingMaximumDepth(-1);
            // Top-level libraries have no parents
            elmTransformerState.putNode(null);
            // Transform the tree rooted at the specified library.
            elmTransformerRecursive.transform(library, elmTransformerState);
        }

        // Transform the bulk elm tree into PySpark Scripts
        var elmToPySparkConverter = new BulkElmToPySparkConverter();        
        var elmToPySparkConverterState = new BulkElmToPySparkConverterState();
        var convertedLibraries = new ArrayList<String>();
        for (Library library : libraryList) {
            var outputNode = elmToPySparkConverter.convert(library, elmToPySparkConverterState);
            var pySparkOutputWriter = new PySparkOutputWriter(0, "    ", "\n");
            outputNode.print(pySparkOutputWriter);
            convertedLibraries.add(pySparkOutputWriter.getDocumentContents());
        }

        return convertedLibraries;
    }
}
