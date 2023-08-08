package gov.va.sparkcql.compiler;

import java.util.List;

import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

public interface Compiler {

    public List<Library> compile(String... cqlText);

    public List<Library> compile(List<VersionedIdentifier> cqlIdentifier);
}