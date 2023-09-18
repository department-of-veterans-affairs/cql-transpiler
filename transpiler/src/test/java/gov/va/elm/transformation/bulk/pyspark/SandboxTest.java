package gov.va.elm.transformation.bulk.pyspark;

import java.util.ArrayList;
import java.util.Set;

import org.hl7.elm.r1.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.transpiler.ElmTransformer;
import gov.va.transpiler.ElmTransformerState;
import gov.va.transpiler.bulk.BulkTransformationLoader;
import gov.va.transpiler.bulk.pyspark.BulkElmToPySparkConverter;
import gov.va.transpiler.bulk.pyspark.BulkElmToPySparkConverterState;
import gov.va.transpiler.bulk.transformation.ModifyLiterals;

public class SandboxTest {

    private CqfCompiler compiler;

    @BeforeEach
    public void setup() {
        compiler = new CqfCompiler(new FileLibrarySourceProvider("./src/test/resources/cql"));
    }

    @Test
    public void test() {
        var libraryList = compiler.compile(
            "define myconst_1: 123\n" +
            "define myconst_b: myconst_1");

        // Transform the CQL ELM tree into a more abstract version designed to be converted into data-based rather than patient-based semantics (the "bulk" elm tree)
        BulkTransformationLoader bulkTransformationLoader = new BulkTransformationLoader();
        bulkTransformationLoader.registerTransformations(Set.of(new ModifyLiterals()));
        ElmTransformer elmTransformer = new ElmTransformer(bulkTransformationLoader);
        for (Library library : libraryList) {
            // Create the state of the for the transformation transversal
            var elmTransformerState = new ElmTransformerState();
            // Setting the depth to -1 means the TransformationState will transverse to the bottom of the tree
            elmTransformerState.enterSubtreeSettingMaximumDepth(-1);
            // Top-level libraries have no parents
            elmTransformerState.putNode(null);
            // Transform the tree, starting with the specified library
            elmTransformer.visitElement(library, elmTransformerState);
        }

        // Transform the bulk elm tree into PySpark Scripts
        var elmToPySparkConverter = new BulkElmToPySparkConverter();        
        var elmToPySparkConverterState = new BulkElmToPySparkConverterState();
        var convertedLibraries = new ArrayList<String>();
        for (Library library : libraryList) {
            convertedLibraries.add(elmToPySparkConverter.convert(library, elmToPySparkConverterState));
        }
        System.out.println(convertedLibraries);
    }
}
