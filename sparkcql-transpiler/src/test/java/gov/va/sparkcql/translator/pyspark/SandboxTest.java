package gov.va.sparkcql.translator.pyspark;

import java.util.Set;

import org.hl7.elm.r1.Literal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gov.va.sparkcql.cqf.compiler.CqfCompiler;
import gov.va.sparkcql.cqf.compiler.FileLibrarySourceProvider;
import gov.va.sparkcql.translator.Transformer;

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

    private static class ModifyLiterals extends PySparkTransformation<Literal> {

        @Override
        public boolean appliesToNode(org.hl7.elm.r1.Element node, org.hl7.elm.r1.Element parentNode) {
            return node instanceof Literal && (((Literal) node).getValue().length() < 10);
        }

        @Override
        public int transform(org.hl7.elm.r1.Element node, org.hl7.elm.r1.Element parentNode) {
            if (appliesToNode(node, parentNode)) {
                Literal currentNode = (Literal) node;
                currentNode.setValue(currentNode.getValue() + "1");
                return 1;
            }
            return 0;
        }
    }
}
