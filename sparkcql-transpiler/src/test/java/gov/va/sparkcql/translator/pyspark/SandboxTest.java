package gov.va.sparkcql.translator.pyspark;

import java.util.Set;

import org.hl7.elm.r1.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.sparkcql.translator.Transformation;
import gov.va.sparkcql.translator.TransformationBucket;
import gov.va.sparkcql.translator.Transformer;

public class SandboxTest {

    private CqfCompiler compiler;

    @BeforeEach
    public void setup() {
        compiler = new CqfCompiler(new FileLibrarySourceProvider("./src/test/resources/cql"));
    }

    @Test
    public void test() {
        var libraryList = compiler.compile("define myconst: 123");
        Transformer transformer = new Transformer(new TransformationBucket() {

            @Override
            public Transformation pullTransformationFromBucket(Element node, Element parentNode) {
                return null;
            }

            @Override
            public void addTransformations(Set<Transformation> transformations) {
                // do nothing
            }
            
        });
        var translator = new CqlToPySparkElmTranslator(transformer);
        var translatedELM = translator.translate(libraryList);
        var converter = new PySparkElmConverter();
        var algorithm = new PySparkElmToScriptEngine();
        String conversion = converter.convert(translatedELM, algorithm); 
        System.out.println(conversion);
    }
}
