package gov.va.sparkcql.repository.cql;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.repository.PointRepository;

public interface CqlSourceRepository extends PointRepository<CqlSource, VersionedIdentifier> {
}