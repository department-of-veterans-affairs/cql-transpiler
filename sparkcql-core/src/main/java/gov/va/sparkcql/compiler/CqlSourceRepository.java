package gov.va.sparkcql.compiler;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.common.repository.Repository;
import gov.va.sparkcql.model.CqlSource;

public interface CqlSourceRepository extends Repository<VersionedIdentifier, CqlSource> {
}