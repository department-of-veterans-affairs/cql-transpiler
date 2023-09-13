package gov.va.sparkcql.translator;

import java.util.List;

import org.hl7.elm.r1.Library;

public interface ElmConverter {
    String convert(List<Library> elm, ElmToScriptEngine algorithm);
}
