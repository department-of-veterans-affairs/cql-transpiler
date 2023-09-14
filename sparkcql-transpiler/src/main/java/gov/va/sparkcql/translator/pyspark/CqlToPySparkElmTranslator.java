package gov.va.sparkcql.translator.pyspark;

import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.sparkcql.translator.CqlElmTranslator;
import gov.va.sparkcql.translator.Transformer;

public class CqlToPySparkElmTranslator implements CqlElmTranslator {

    private Transformer transformer;

    public CqlToPySparkElmTranslator(Transformer cqlToPySparkTranslator) {
        this.transformer = cqlToPySparkTranslator;
    }

    @Override
    public List<Library> translate(List<Library> cql) {
        for (Library library : cql) {
            transformer.applyTransformation(library, null, -1);
        }
        // TODO: deep copy CQL tree
        // TODO: create framework for complex translations of Libraries
        return cql;
    }
}