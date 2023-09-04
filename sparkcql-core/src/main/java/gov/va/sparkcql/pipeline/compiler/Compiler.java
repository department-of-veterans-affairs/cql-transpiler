package gov.va.sparkcql.pipeline.compiler;

import java.util.List;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.Component;
import gov.va.sparkcql.types.QualifiedIdentifier;

public interface Compiler extends Component {

    public Plan compile(String... anonymousCql);

    public Plan compile(List<QualifiedIdentifier> identifiedCql);

    public Plan compile(List<QualifiedIdentifier> identifiedCql, String... anonymousCql);
}