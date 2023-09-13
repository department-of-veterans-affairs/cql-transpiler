package gov.va.sparkcql.translator.pyspark;

import java.util.List;

import org.hl7.elm.r1.Library;

import gov.va.sparkcql.translator.ElmConverter;
import gov.va.sparkcql.translator.ElmToScriptEngine;

public class PySparkElmConverter implements ElmConverter {

    @Override
    public String convert(List<Library> elm, ElmToScriptEngine engine) {
        var state = new PysparkElmToScriptEngineState();
        // TODO
        for (Library library : elm) {
            engine.visitLibrary(library, state);
        }
        return state.toScript();
    }
}
