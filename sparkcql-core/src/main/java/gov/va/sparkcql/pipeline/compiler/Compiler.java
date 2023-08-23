package gov.va.sparkcql.pipeline.compiler;

import java.util.List;

import gov.va.sparkcql.domain.LibraryCollection;
import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.pipeline.Component;

public interface Compiler extends Component {

    public LibraryCollection compile(String... cqlText);

    public LibraryCollection compile(List<VersionedIdentifier> cqlIdentifier);
}