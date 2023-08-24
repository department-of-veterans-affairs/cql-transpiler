package gov.va.sparkcql.pipeline.compiler;

import java.util.List;

import gov.va.sparkcql.domain.LibraryCollection;
import gov.va.sparkcql.pipeline.Stage;
import org.hl7.elm.r1.VersionedIdentifier;

public interface Compiler extends Stage {

    public LibraryCollection compile(String... cqlText);

    public LibraryCollection compile(List<VersionedIdentifier> cqlIdentifier);
}