package gov.va.sparkcql.translator.pyspark;

import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.sparkcql.translator.CqlElmTranslator;

public class CqlToPySparkElmTranslator implements CqlElmTranslator {
    @Override
    public List<Library> translate(List<Library> cql) {
        // TODO: deep copy CQL
        // TODO: create framework for complex tranlsations of Libraries
        return cql;
    }
}