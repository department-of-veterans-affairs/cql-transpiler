package gov.va.sparkcql.translator;

import java.util.List;

import org.hl7.elm.r1.Library;

public interface CqlElmTranslator {

    /**
     * @param cql Root node of the ELM tree for the compiled CQL.
     * @return Root node for the translated ELM tree.
     */
    List<Library> translate(List<Library> cql);
}
