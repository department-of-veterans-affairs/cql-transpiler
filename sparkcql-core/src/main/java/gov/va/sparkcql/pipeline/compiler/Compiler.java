package gov.va.sparkcql.pipeline.compiler;

import java.util.List;

import org.hl7.elm.r1.Library;
import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.pipeline.Component;

public interface Compiler extends Component {

    public List<Library> compile(String... cqlText);

    public List<Library> compile(List<VersionedIdentifier> cqlIdentifier);
}