package gov.va.sparkcql.pipeline.repository.cql;

import org.hl7.elm.r1.VersionedIdentifier;

import gov.va.sparkcql.domain.CqlSource;
import gov.va.sparkcql.pipeline.repository.ReadableRepository;

public interface CqlSourceRepository extends ReadableRepository<CqlSource, VersionedIdentifier> {
}