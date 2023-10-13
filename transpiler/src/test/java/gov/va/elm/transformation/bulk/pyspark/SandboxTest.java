package gov.va.elm.transformation.bulk.pyspark;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.hl7.elm.r1.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.transpiler.ElmTransformerState;
import gov.va.transpiler.bulk.BulkTransformationLoader;
import gov.va.transpiler.bulk.pyspark.BulkElmToPySparkConverter;
import gov.va.transpiler.bulk.pyspark.BulkElmToPySparkConverterState;
import gov.va.transpiler.bulk.pyspark.PySparkOutputWriter;
import gov.va.transpiler.bulk.pyspark.utilities.CQLNameToPythonName;
import gov.va.transpiler.bulk.pyspark.utilities.CQLTypeToPythonType;
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

        System.out.println("# Original CQL ");
        System.out.println(cql);
        System.out.println();

        var pyspark = processCQLToPySpark(cql);
        for (String output : pyspark) {
            System.out.println(output);
        }
        assertEquals(1, pyspark.size());
    }

    @Test
    public void testDefineTuple() {
        String cql = ""
            + "library TestCQL version '2.1'\n"
            + "define \"My Tuple\": Tuple { a: 1, b: 'foo' }\n"
            + "define myconst: \"My Tuple\".a"
            ;

        System.out.println("# Original CQL ");
        System.out.println(cql);
        System.out.println();

        var pyspark = processCQLToPySpark(cql);
        for (String output : pyspark) {
            System.out.println(output);
        }
        assertEquals(1, pyspark.size());
    }

    @Test
    public void testRetrieval() {
        // TODO Aliases and multiline returns should be handled by the context
        String cql = ""
            + "library Retrievals version '1.0'\n"
            + "using  FHIR version '4.0.1'\n"
            + "context Patient\n"
            //+ "define \"Encounter A\": [Encounter]\n"
            + "define \"Encounter B\": [Encounter] E where E.status.value = 'planned'\n"
            ;

        System.out.println("# Original CQL ");
        System.out.println(cql);
        System.out.println();

        var pyspark = processCQLToPySpark(cql);
        for (String output : pyspark) {
            System.out.println(output);
        }
        assertEquals(1, pyspark.size());
    }

    private List<String> processCQLToPySpark(String cql) {
        var libraryList = compiler.compile(cql);

        // Transform the CQL ELM tree into a more abstract version designed to be converted into data-based rather than patient-based semantics (the "bulk" elm tree)
        BulkTransformationLoader bulkTransformationLoader = new BulkTransformationLoader();
        //bulkTransformationLoader.registerTransformations(Set.of(new ModifyLiterals()));
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
        var cqlTypeToPythonType = new CQLTypeToPythonType();
        var cqlNameToPythonName = new CQLNameToPythonName();
        var elmToPySparkConverter = new BulkElmToPySparkConverter(cqlTypeToPythonType, cqlNameToPythonName);        
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
