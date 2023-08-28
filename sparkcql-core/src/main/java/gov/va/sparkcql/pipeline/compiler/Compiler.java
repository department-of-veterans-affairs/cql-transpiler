package gov.va.sparkcql.pipeline.compiler;

import java.util.List;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.Component;
import org.hl7.elm.r1.VersionedIdentifier;

public interface Compiler extends Component {

    public Plan compile(String... cqlText);

    public Plan compile(List<VersionedIdentifier> cqlIdentifier);
}