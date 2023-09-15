package gov.va.sparkcql.translator.pyspark;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.sparkcql.translator.Transformer;
import gov.va.sparkcql.translator.pyspark.transformationimpls.ModifyLiterals;

public class SandboxTest {

    private CqfCompiler compiler;

    @BeforeEach
    public void setup() {
        compiler = new CqfCompiler(new FileLibrarySourceProvider("./src/test/resources/cql"));
    }

    @Test
    public void test() {
        var libraryList = compiler.compile("define myconst: 123");
        PySparkTransformationBucket bucket = new PySparkTransformationBucket(); 
        bucket.addTransformations(Set.of(new ModifyLiterals()));
        Transformer transformer = new Transformer(bucket);
        var translator = new CqlToPySparkElmTranslator(transformer);
        var translatedELM = translator.translate(libraryList);
        var converter = new PySparkElmConverter();
        var algorithm = new PySparkElmToScriptEngine();
        String conversion = converter.convert(translatedELM, algorithm); 
        System.out.println(conversion);
    }
}
