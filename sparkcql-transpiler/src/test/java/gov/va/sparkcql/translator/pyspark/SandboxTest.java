package gov.va.sparkcql.translator.pyspark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;

public class SandboxTest {

    private CqfCompiler compiler;

    @BeforeEach
    public void setup() {
        compiler = new CqfCompiler(new FileLibrarySourceProvider("./src/test/resources/cql"));
    }

    @Test
    public void test() {
        var libraryList = compiler.compile("define myconst: 123");
        var translator = new CqlToPySparkElmTranslator();
        var translatedELM = translator.translate(libraryList);
        var compiler = new PySparkElmConverter();
        var algorithm = new PySparkElmToScriptEngine();
        String conversion = compiler.convert(translatedELM, algorithm);
        System.out.println(conversion);
    }
}
